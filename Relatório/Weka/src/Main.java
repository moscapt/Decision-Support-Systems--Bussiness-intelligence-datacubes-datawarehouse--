import java.io.FileReader;
import java.util.ArrayList;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import weka.associations.Apriori;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

public class Main {
	public static void main (String[] args) throws Exception
	{
		//reader();
		//gerar os ficheiros arff
		ArrayList<String> listFiles = new ArrayList<>();
		for (int i = 1; i <= 3; i++)
		{
			String file = "C:\\Users\\topog\\Desktop\\pdi-ce-7.0.0.0-25\\TASKDATA" + i;
			transform(file + ".ktr");
			listFiles.add(file + ".arff");
			FileReader fr = new FileReader(file + ".csv");
			Data cc = new Data (fr,i);
			cc.readLines();
			cc.buildAttributes();
			cc.builData();
			cc.write();		
		}
		for (String path : listFiles)
		{
			System.out.println(path + "\n");
			DataSource source = new DataSource(path);
			Instances data = source.getDataSet();
			Apriori model = new Apriori();
			model.setDelta(0.05);
			model.setLowerBoundMinSupport(0.1);
			model.setMinMetric(0.6);
			model.setUpperBoundMinSupport(0.4);
			model.buildAssociations(data);
			System.out.println(model);
		}
	}
	public static void transform (String pathName) throws Exception
	{
		KettleEnvironment.init();
		TransMeta transMeta = new TransMeta(pathName);
		Trans trans = new Trans(transMeta);
		
		
		trans.execute(null);
		trans.waitUntilFinished();
		if (trans.getErrors() > 0)
		{
			throw new RuntimeException("there were some errors during the transformation execution");
		}
	}
}