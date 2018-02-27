
public class edge {
	
	Node   destination;
	double section;
	double edgeVolume, edgeTemperature;
	
	edge(Node destination, double section)
	{
		this.destination = destination;
		this.section = section;
		edgeVolume = 0; edgeTemperature = 0;
	}
}
