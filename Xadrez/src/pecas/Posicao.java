package pecas;

public class Posicao
{
	private byte x;
	private byte y;
        
        
        public Posicao(byte x, byte y){
            this.setX(x);
            this.setY(y);
        }

    public Posicao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
	public byte getX() {
		return x;
	}
	public void setX(byte x) {
		this.x = x;
	}
	public byte getY() {
		return y;
	}
	public void setY(byte y) {
		this.y = y;
	}	
}