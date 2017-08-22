import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class Data {
	private CSVReader file;
	private ArrayList<String> attributes;
	private ArrayList<String> dataValues;
	private ArrayList<String[]> lines;
	private int numberFile;
	public Data(FileReader csv, int numberFile)
	{
		this.numberFile = numberFile;
		this.file = new CSVReader(csv);
		attributes = new ArrayList<>();
		dataValues = new ArrayList<>();
		lines = new ArrayList<>();
	}
	//metodo para ler do ficheiro e formatar os valores
	public void readLines() throws IOException
	{
		String [] line = file.readNext();
		//le dos ficheiros
		while(line != null)
		{
			line = file.readNext();
			lines.add(line);
		}
		//formata os valores
		for (String [] aa : lines)
		{
			try
			{
				int a = lines.indexOf(aa);
				if (aa[0].contains(","))
				{
					String [] bb;
					bb = aa[0].split(", ");
					lines.set(a, bb);
				}
			}catch (Exception e) {
				break;
			}
		}
	}
	
	public void buildAttributes()
	{
		//listagens de attributos existentes na amostra de dados
				for (String [] aa : lines)
				{
					try
					{
					for (String a : aa)
					{
						if (!attributes.contains(a))
						{
							attributes.add(a);
						}		
					}
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
				
				//novo codigo
			
				ArrayList<String> remove = new ArrayList<>();
				for (String values : attributes)
				{
					int i = attributes.indexOf(values);
					if (Character.isWhitespace(values.charAt(0)))
					{
						remove.add(attributes.get(i));
					}
				}
				for (String values : remove)
				{
					attributes.remove(values);
				}
	}
	public void builData()
	{
		try
		{
			ArrayList<String> aux = new ArrayList<>();
			for (String a : attributes)
			{
				aux.add(a);
				aux.add(" ");
			}
		

		for (String [] aa : lines)
		{
			String res = "";
			int i = 1;
			for (String jessus : attributes)
			{
				if(i++ == attributes.size() )
				{
					res += "?";
				}else
				{
					res += "?,";
				}
			}
			//res = "?,?,?,?,?,?,?";
			StringBuilder gg = new StringBuilder(res);
			for (String a : aa)
			{
				if (Character.isWhitespace(a.charAt(0)))
				{
					a = a.substring(1);
				}
				
				if (aux.contains(a))
				{
					int index = aux.indexOf(a);
					
					gg.setCharAt(index,'Y');
					
				}
			}
			res = gg.toString();
			dataValues.add(res);
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		for (String values : attributes)
		{
			int index = attributes.indexOf(values);
			values = values.replace(" ", "_");
			attributes.set(index, values);
		}
		
	}
	
	public ArrayList<String> getDataValues()
	{
		return dataValues;
	}
	public ArrayList<String> getAttributes()
	{
		return attributes;
	}
	public void write ()
	{
		String path = "C:\\Users\\topog\\Desktop\\pdi-ce-7.0.0.0-25\\TASKDATA" + numberFile + ".arff";
		File file = new File(path);
		try
		{
			Writer arff = new OutputStreamWriter(new FileOutputStream(file),"UTF8");
			arff.append("@Relation TASKDATA" + numberFile +"\n");
			for (String values : attributes)
			{
				arff.append("@Attribute ").append(values).append(" {'?',Y}\n");
			}
			arff.append("\n@data\n");
			for (String values : dataValues)
			{
				arff.append(values).append("\n");
			}
			
			arff.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
