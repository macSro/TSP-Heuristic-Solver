package tsp_narzedzia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import tsp.Miasto;
import tsp.Osobnik;
import tsp.Populacja;
import tsp.Wynik;

public class Narzedzia {

	public static Osobnik kopiuj(Osobnik o) {
		Osobnik o_kopia;
		
		int l_miast = o.getMiasta().size();
		ArrayList<Miasto> m_kopia = new ArrayList<Miasto>(l_miast);
		
		Miasto m;
		for(int i=0; i<l_miast; i++) {
			m = o.getMiasta().get(i);
			m_kopia.add(new Miasto(m.getNumer(), m.getX(), m.getY(), m.getTyp()));
		}
		
		o_kopia = new Osobnik(m_kopia);
		
		return o_kopia;
	}
	
	public static void zapiszLog(Populacja p, int id) {
		try {
			File fout = new File("LOG/log_pop" + (id+1) + ".txt");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("POPULACJA " + (id+1));
			bw.newLine();
			bw.newLine();
			for (int i = 0; i < p.getOsobniki().size(); i++) {
				bw.write("Osobnik " + (i+1) + ": ");
				for(int j=0; j<p.getOsobniki().get(i).getMiasta().size(); j++) {
					bw.write(p.getOsobniki().get(i).getMiasta().get(j).getNumer() + ", ");
				}
				bw.newLine();
			}
			bw.newLine();
			bw.write("Najlepszy dystans: " + p.obliczNajlepszyDystans());
			bw.newLine();
			bw.write("Sredni dystans: " + p.obliczSredniDystans());
			bw.newLine();
			bw.write("Najgorszy dystans: " + p.obliczNajgorszyDystans());
			bw.newLine();
			
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void zapiszExcel(ArrayList<Wynik> wyniki, String nazwa, boolean dopis, String plik, String rP, String lG, String pX, String pM, String n) {
		try {
			File fout = new File("EXCEL/" + nazwa + ".xls");
			FileOutputStream fos = new FileOutputStream(fout,dopis);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("Nazwa pliku" + "\t" + "Rozmiar populacji" + "\t" + "Liczba generacji" + "\t" + "Prawd. krzyøowania" + "\t" + "Prawd. mutacji" + "\t" + "Rozmiar turnieju");
			bw.newLine();
			bw.write(plik + "\t" + rP + "\t" + lG + "\t" + pX + "\t" + pM + "\t" + n);
			bw.newLine();
			bw.newLine();
			bw.write("Nr generacji\tNajlepszy\tåredni\tNajgorszy");
			bw.newLine();
			for (int i = 0; i < wyniki.size(); i++) {
				bw.write("" + (i+1));
				bw.write("\t");
				bw.write("=WARTOå∆.LICZBOWA(\""+ wyniki.get(i).getNajlepszy() + "\";\".\")");
				bw.write("\t");
				bw.write("=WARTOå∆.LICZBOWA(\""+ wyniki.get(i).getSredni() + "\";\".\")");
				bw.write("\t");
				bw.write("=WARTOå∆.LICZBOWA(\""+ wyniki.get(i).getNajgorszy() + "\";\".\")");
				bw.write("\t");
				bw.newLine();
			}
			bw.newLine();
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void zapiszExcelCSV(ArrayList<Wynik> wyniki, String nazwa, boolean dopis, String plik, String rP, String lG, String pX, String pM, String n) {
		try {
			File fout = new File("EXCEL/" + nazwa + ".csv");
			FileOutputStream fos = new FileOutputStream(fout,dopis);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("Nazwa pliku;Rozmiar populacji;Liczba generacji;Prawd. krzyøowania;Prawd. mutacji;Rozmiar turnieju");
			bw.newLine();
			bw.write(plik + ";" + rP + ";" + lG + ";" + pX + ";" + pM + ";" + n);
			bw.newLine();
			bw.newLine();
			bw.write("Nr generacji;Najlepszy;åredni;Najgorszy");
			bw.newLine();
			for (int i = 0; i < wyniki.size(); i++) {
				bw.write((i+1) + ";" + wyniki.get(i).getNajlepszy() + ";" + wyniki.get(i).getSredni() + ";" + wyniki.get(i).getNajgorszy());
				bw.newLine();
			}
			bw.newLine();
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
