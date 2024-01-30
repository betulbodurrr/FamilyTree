package famtree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FamTree {

    public static void main(String[] args) throws IOException {

        int sira = 0, sutun = 0, temp = 0;

        ArrayList<Kisi> kisiler = new ArrayList<Kisi>();
        Kisi yeniKisi = new Kisi();

        for (int i = 1; i <= 4; i++) {

            String file = ".\\src\\Excel\\page" + i + ".csv";
            BufferedReader reader = null;
            String line = "";

            temp = 0; // temp excellerin ilk satirinin list'e kaydedilmemesi icin var. Ilk sirada "id, isim, ..." var.

            try {
                reader = new BufferedReader(new FileReader(file));

                while ((line = reader.readLine()) != null) {

                    String[] row = line.split(";");                   
                    
                    if (temp != 0) {

                        kisiler.add(new Kisi()); //Her bir satir yeni bir kisi demektir.
                        yeniKisi = kisiler.get(sira);
                        sutun = 0;

                        for (String index : row) {

                            //System.out.printf("%-20s", index); // Burada satirdaki elemenlar ";" elemanina göre parcalaniyor ve teker teker sout icinde yaziliyor.

                            switch (sutun) {
                                case 0:
                                    yeniKisi.id = Integer.parseInt(index);
                                    break;
                                case 1:
                                    yeniKisi.ad = index;
                                    break;
                                case 2:
                                    yeniKisi.soyad = index;
                                    break;
                                case 3:
                                    yeniKisi.dogumTarihi = index;
                                    break;
                                case 4:
                                    yeniKisi.esi = index;
                                    break;
                                case 5:
                                    yeniKisi.anneAdi = index;
                                    break;
                                case 6:
                                    yeniKisi.babaAdi = index;
                                    break;
                                case 7:
                                    yeniKisi.kanGrubu = index;
                                    break;
                                case 8:
                                    yeniKisi.meslek = index;
                                    break;
                                case 9:
                                    yeniKisi.medeniHal = index;
                                    break;
                                case 10:
                                    yeniKisi.kizlikSoyadi = index;
                                    break;
                                case 11:
                                    yeniKisi.cinsiyet = index;
                                    break;
                                default:
                                    break;
                            }

                            sutun++; // id -> cinsiyet arasinda geziyorum.
                        }

                        sira++; // 1. siradan sona kadar gidiyorum. 0. sira id, ad, soyad vs.
                    }

                    temp = 1;
                    //System.out.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        }

        for (int i = 0; i < kisiler.size(); i++) // farklı excelde olan ayni kisileri temizleidm.
        {
            for (int j = i + 1; j < kisiler.size(); j++) {
                if (kisiler.get(i).id == kisiler.get(j).id) {
                    //System.out.println(kisiler.get(j).ad + " " + kisiler.get(j).soyad);
                    kisiler.remove(j);
                }
            }
        }

        System.out.println(kisiler.size() + " Kisi Listelendi.");

        //Buraya kadar csv dosyalarindaki kisileri arrayList icerisine kayit ettim.  
        
        CreateTree create = new CreateTree();

        SoyadDuzelt(kisiler);

        esDugumKontrol(kisiler);

        create.Create(kisiler);

        /*for (int i = 0; i < kisiler.size(); i++) {
            System.out.println(kisiler.get(i).id+" "+kisiler.get(i).ad + " " + kisiler.get(i).soyad);
        }*/
    }

    public static void esDugumKontrol(ArrayList<Kisi> kisiler) {

        String isim;
        int temp = 0, indis = 0;

        for (int i = 0; i < kisiler.size(); i++) {

            temp = 0;

            for (int j = 0; j < kisiler.size(); j++) {

                isim = kisiler.get(j).ad + " " + kisiler.get(j).soyad;

                if (kisiler.get(i).esi.equals(isim) && kisiler.get(j).esi.length() != 0) {
                    temp++;
                }
            }

            //System.out.println(temp);
            if (temp == 0 && kisiler.get(i).esi.length() != 0) {

                Kisi ekleKisi = new Kisi(kisiler.size() + 1);

                indis = kisiler.get(i).esi.indexOf(" ");
                ekleKisi.ad = kisiler.get(i).esi.substring(0, indis);
                ekleKisi.soyad = kisiler.get(i).soyad;

                ekleKisi.esi = kisiler.get(i).ad + " " + kisiler.get(i).soyad;

                kisiler.add(ekleKisi);

                //System.out.println(ekleKisi.ad + " " + ekleKisi.soyad);
            }
        }
    }

    public static void SoyadDuzelt(ArrayList<Kisi> kisiler) {

        String esSoyad;
        int indis;

        for (int i = 0; i < kisiler.size(); i++) {
            
            indis = kisiler.get(i).esi.indexOf(" ");

            if (indis < 0) {
                indis = kisiler.get(i).esi.length();
            }

            if (indis != 0) { // indis sıfır olanlar esi olmayanlar
                kisiler.get(i).esi = kisiler.get(i).esi.substring(0, indis) + " " + kisiler.get(i).soyad;
            }

            //System.out.println(kisiler.get(i).ad + " " + kisiler.get(i).esi);
        }

    }

}