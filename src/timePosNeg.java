//positive negative counts at different time
public class timePosNeg {
	double time;
	int posCount=0; //number of positive ratings at the time
	int negCount=0; //number of negative ratings at the time
	public timePosNeg(double t, int pos, int neg)
	{
		this.time = t;
		this.posCount = pos;
		this.negCount =neg;
		
	}
}
