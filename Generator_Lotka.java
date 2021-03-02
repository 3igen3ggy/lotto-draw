//SOURCE: https://www.wynikilotto.net.pl/lotto/wyniki/
//since draw nr: 6200 z dnia 03.01.2019r.
//till draw nr: 6537	z dnia 27.02.2021r.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Generator_Lotka {
	static int ilosc_losowan = 6537 - 6200 + 1; //338
	
	public static void main(String[] args) {
		
		int[][] table = importFromFile("lotek.txt");
		
		//count how many times certain number was drawn
		int[] counts = new int[49];
		for (int i = 0; i < ilosc_losowan; i++) {
			for (int j = 0; j < 6; j++) {
				counts[table[i][j] - 1]++;
			}
		}
		
		System.out.println(Arrays.toString(counts));
		
		//array of probabilities
		double[] prob = new double[49];
		for (int i = 0; i < 49; i++) {
			prob[i] = counts[i] / (double) (ilosc_losowan * 6);
		}
		
		System.out.println(Arrays.toString(prob));

		//cumulative array
		double[] cumulative = new double[49];
		double sum = 0;
		for (int i = 0; i < 49; i++) {
			cumulative[i] = sum;
			sum += prob[i];
		}

		System.out.println(Arrays.toString(cumulative));
		double rand = Math.random();
		System.out.println(rand);
		System.out.println(numberDrawn(rand, cumulative));
	}
	
	public static int numberDrawn(double rand, double[] cumulative) {
		
		int i = 0;
		
		while (rand > cumulative[i]) {
			i++;			
		}
		return i;
		
	}
	
	public static int[][] importFromFile(String filename) {
		String workingDirectory = System.getProperty("user.dir");
		String file = workingDirectory + "/" + filename;
		String line = "";
		String[] strMass = null;
		int[][] table = new int [338][6];
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			//generate matrix for imported values

			String[][] tableAux = new String [338][];
			
			//generate arrays with single lotto draw
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(" ");
				String[] rowAux = row.clone();
				tableAux[i] = rowAux.clone();
				i++;
//				System.out.println(Arrays.toString(row));
			}
		
			//convert String[][] -> int[][]
			for (i = 0; i < ilosc_losowan; i++) {
				for (int j = 0; j < 6; j++) {
					table[i][j] = Integer.parseInt(tableAux[i][j]);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return table;
	}

}
