// Low-level binary tree implementation on physical files
// The ECOReader interacts with it to use the opening move database
// This was modified from a class I originally wrote for a different application
// so some of the functions in here are never used by the chess program
// Techincally the tree isn't really binary, but rather "oct"-nary", as it has
// branches at each board coordinate, which can run from 0 to 7. 

import java.lang.*;
import java.io.*;
import java.util.*;

class ECODatabase {
	String dbFile;
	String codeFile;
	
	public int dataCount;
	public int[] fID;
		
	// Constructor creates a new instance of the tree interface
	public ECODatabase(String df, String ff) {
		dbFile = df;
		codeFile = ff;
	}
	
	public static int[] convert(long n) {
		int[] bits = new int[3];
		bits[0] = (int)(n%256);
		n = (n - bits[0])/256;
		bits[1] = (int)(n%256);
		bits[2] = (int)((n - bits[1])/256);
		return bits;
	}
	
	public static int[] convert2(long n) {
		int[] bits = new int[2];
		bits[0] = (int)(n%256);
		bits[1] = (int)((n - bits[0])/256);
		return bits;
	}
	
	public void writeAddr(RandomAccessFile raf, int[] bits) {
		try {
			for(int i=0;i<bits.length;i++)
				raf.write(bits[i]);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void writeData(RandomAccessFile raf, int[] bits) {
		writeAddr(raf,bits);
	}
	
	public void writeEntry(RandomAccessFile raf, int fid) {
		try {
			int[] bits = convert2(fid);
			writeData(raf,bits);
			writeData(raf,convert2(0)); // Mark end of all word data
			writeAddr(raf,convert(0)); // Leave space for future continuation	
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public long getAddr(RandomAccessFile raf) {
		try {
			return raf.read() + raf.read()*256 + raf.read()*256*256;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return -1;
	}
	
	public long getData(RandomAccessFile raf) {
		try {
			return raf.read() + raf.read()*256;
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return -1;
	}
	
	// Adds the given opening (a series of moves in the word) to the tree
	public void addOpeningData(String word, int gfid) {
		int fileID = gfid;
		System.out.println("adding " + word + " " + gfid);
		try {
			RandomAccessFile db = new RandomAccessFile(dbFile,"rw");
				db.seek(0);
				boolean entryWritten = false;
				for(int j=0;j<=word.length();j++) {
					char currentChar = (char)db.read();
					char readChar = 1;
					if(j<word.length())readChar = word.charAt(j);
					if(currentChar==readChar) { // Character in index matches next character in word
						long offset = getAddr(db);
						db.seek(offset);
					} else if(currentChar==0) { // End of index at this physical location
						long offset = getAddr(db);
						if(offset>0) { // Index continues at different physical location
							db.seek(offset);
							j--;
						}
						else { // Complete end of index
							db.seek(db.getFilePointer()-3);
							long fileLength = db.length();
							int[] bits = convert(fileLength);
							writeAddr(db,bits); // Write where index will continue
							db.seek(fileLength);
							db.write((int)readChar);
							bits = convert(fileLength+8);
							writeAddr(db,bits); // Add reference to entry for this character
							db.write(0);
							writeAddr(db,convert(0)); // Add end-of-index entry
							if(readChar!=1) { // Add blank entry for next character
								db.write(0); 
								writeAddr(db,convert(0));
								db.seek(db.getFilePointer()-4);
							} else { // End of word, so actual entry must be written here
								writeAddr(db,convert(0)); // Leave space for word ID
								writeEntry(db,fileID);
								entryWritten = true;
							}
						}
					} else {
						j--;
						db.read();db.read();db.read();
					}
				}
				if(!entryWritten) { // Word data location reached
					long offset = db.getFilePointer();
					boolean firstTime = true;
					while(offset!=0) { // Find empty spot to write to
						db.seek(offset);
						if(firstTime){long wID = getAddr(db);}
						firstTime = false;
						long fid = getData(db);
						while(fid!=0){ // Find next segment offset
							fid  = getData(db); // next file id is here
						}
						offset = getAddr(db);
					}
					db.seek(db.getFilePointer()-3);
					long fileLength = db.length();
					int[] bits = convert(fileLength);
					writeAddr(db,bits); // Write end of file address, where new entry will be
					db.seek(fileLength);
					writeEntry(db,fileID);
				}
			
			db.close();
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
		
	// Clears the entire tree
	public void clearDatabase() {
		try {
			RandomAccessFile raf = new RandomAccessFile(dbFile,"rw");
			raf.write(0);
			raf.write(0);
			raf.write(0);
			raf.write(0);
			raf.setLength(4);
			raf.close();
			raf = new RandomAccessFile(codeFile,"rw");
			raf.setLength(0);
			raf.close();
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	
	// Compresses the tree so that not only does it take up less space, but
	// retrieval is also faster as every depth level is grouped together
	public void packDatabase() {
		try {
			long rwcount = 0;
			long allcount = 0;
			RandomAccessFile odb = new RandomAccessFile(dbFile,"r");
			RandomAccessFile pdb = new RandomAccessFile(dbFile + ".pack","rw");
			pdb.setLength(4);
			pdb.write(0);
			writeAddr(pdb,convert(0));
			long offset = 4;
			int level = 0;
			long[] levelOffset = new long[200];
			levelOffset[0] = 4;
			long[][] addr = new long[200][50];
			int[] z = new int[200];
			z[0] = 0;
			boolean reload = true;
			boolean[] existsBottom = new boolean[200];
			long[] bottomOffset = new long[200];
			long[] bottomAddrLoc = new long[200];
			char[] curBuffer = new char[200];
			while(true) {
				int k=0;
				if(reload) {
					while(offset!=0) {
						odb.seek(offset);
						int curChar = odb.read();
						while(curChar!=0) {
							addr[level][k] = getAddr(odb);
							k++;
							pdb.write(curChar);
							writeAddr(pdb,convert(0));
							curChar = odb.read();
						}
						offset = getAddr(odb);
					}
					pdb.write(0);
					writeAddr(pdb,convert(0));
				}
				pdb.seek(levelOffset[level]);
				int curChar = pdb.read();
				if(curChar==1) {
					bottomAddrLoc[level] = pdb.getFilePointer();
					existsBottom[level] = true;
					bottomOffset[level] = addr[level][z[level]];
					levelOffset[level]+=4;
					pdb.seek(levelOffset[level]);
					curChar = pdb.read();
					z[level]++;
				}
				if(curChar==0){
					 if(existsBottom[level]) {
						String curWord = new String(curBuffer,0,level);
						offset = bottomOffset[level];
						pdb.seek(bottomAddrLoc[level]);
						writeAddr(pdb,convert(pdb.length()));
						pdb.seek(pdb.length());
						writeAddr(pdb,convert(rwcount));
						boolean firstTime = true;
						while(offset!=0) {
							odb.seek(offset);
							if(firstTime){long oldwid = getAddr(odb);}
							firstTime = false;
							long fid = getData(odb);
							while(fid!=0) {
								writeData(pdb,convert2(fid));
								fid = getData(odb);
							}
							offset = getAddr(odb);
						}
						writeData(pdb,convert2(0));
						writeAddr(pdb,convert(0));	
						rwcount++;
					 }
					 reload = false;
					 level--;
					 if(level==-1)break;
					 z[level]++;
					 levelOffset[level]+=4;
					 offset = addr[level][z[level]];
				} else {
					curBuffer[level] = (char)curChar;
					reload = true;
					offset = addr[level][z[level]];
					writeAddr(pdb,convert(pdb.length()));
					pdb.seek(pdb.length());
					level++;
					existsBottom[level] = false;
					levelOffset[level] = pdb.length();
					z[level] = 0;
				}
			}
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public int value(String s) {
		try {
			return Integer.valueOf(s).intValue();
		} catch (Exception exc) {
			return -1;
		}
	}
	
	// Returns the opening with the given id
	public String getOpening(int id) {
		try {
			BufferedReader cReader = new BufferedReader(new FileReader(codeFile));
			String cLine = cReader.readLine();
			while(cLine!=null) {
				if(value(cLine)==id) {
					cReader.readLine();
					String info = "";
					for(int i=0;i<4;i++)info = info+"\n"+cReader.readLine();
					cReader.close();
					return info.substring(2);
				}
				cLine = cReader.readLine();
			}
			cReader.close();
		} catch(Exception exc) {
			System.out.println("Cannot read code file " + codeFile );
		}
		return "none";
	}
	
	public String getOpeningInfo(String word) {
		int[] mi = getMove(word,true);
		if(mi==null)return "none";
		return getOpening(mi[0]);
	}
	
	public int[] getMove(String word) {
		return getMove(word,false);
	}
	
	// Gets the next from the opening move database, based on moves made so far
	public int[] getMove(String word, boolean getInfo) {
		fID = new int[5000];
		dataCount = 0;
		long offset = 0;
		long wordID = -1;
		boolean foundmatch = false;
		try {
			RandomAccessFile db = new RandomAccessFile(dbFile,"r");
			db.seek(4);
			boolean notfound = false;	
			long startoffset = 4;		
			db.seek(4);
			for(int j=0;j<=word.length();j++) {
				char currentChar = (char)db.read();
				char readChar = 2;
				if(getInfo)readChar=1;
				if(j<word.length())readChar = word.charAt(j);
				if(currentChar==readChar) { // Character in index matches next character in word
					if(readChar==1)foundmatch=true;
					offset = getAddr(db);
					db.seek(offset);
					startoffset = offset;
				} else if(currentChar==0) { // End of index at this physical location
					offset = getAddr(db);
					if(offset>0) { // Index continues at different physical location
						db.seek(offset);
						j--;
					}
					else { // Complete end of index, move not found
						if(readChar==2) { // Opening moves branch off from this point
							int[] chosen = new int[4];
							for(int i=0;i<4;i++) {
								db.seek(startoffset);
								currentChar = (char)db.read();
								char[] valids = new char[9];
								long[] voffset = new long[9]; 
								int n=0;
								boolean existsValid = false;
								while(currentChar!=0) {
									// Find all valid moves extending from this one
									valids[n] = currentChar;
									if(valids[n]>1)existsValid = true;
									voffset[n] = startoffset+n*4+1;
									n++;
									db.read();db.read();db.read();
									currentChar = (char)db.read();
								}	
								if(!existsValid)return null;
								int pick = -1;
								while(pick==-1) {
									// Pick the next move with a probability
									// so that the more common (i.e. deemed better) 
									// moves are picked more often
									for(int o=n-1;o>=0;o--) {
										if(valids[o]<2)continue;
										double prob = n-o+2;
										if(Math.random()<1/prob) {
											pick = o;
											break;
										}
									}
								}
								chosen[i] = (int)(valids[pick])-48;
								db.seek(voffset[pick]);
								startoffset = getAddr(db);
							}
							db.close();
							return chosen;
						}
						notfound = true;
						break;
					}
				} else {
					j--;
					db.read();db.read();db.read();
				}
			}
			if(getInfo&&foundmatch) {
				int[] chosen = new int[1];
				offset = getAddr(db);
				chosen[0] = (int)getData(db);
				return chosen;
			}
			db.close();
			return null;
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
}