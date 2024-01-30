package famtree;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateTree {

    URL duz = this.getClass().getResource("/Excel/duz.png");
    URL alt = this.getClass().getResource("/Excel/alt.png");

    public class Node {

        int id;
        int x;
        int y;

        int kardesKontrol;
        int esKontrol;
        int cocukKontrol;
        int UkardesKontrol;
        ArrayList<Integer> kidsID = new ArrayList<Integer>();
        ArrayList<Integer> siblingsID = new ArrayList<Integer>();
        ArrayList<Integer> UsiblingsID = new ArrayList<Integer>();

        Node es;
        Node kardes;
        Node cocuk;
        Node anne;
        Node baba;
        Node Ucocuk;
        Node Ukardes;

        Node(int id, int x, int y) {

            this.id = id;
            this.x = x;
            this.y = y;

            es = cocuk = kardes = anne = baba = null;
            kardesKontrol = esKontrol = cocukKontrol = UkardesKontrol = 0;
        }
    }

    ArrayList<Node> dugumler = new ArrayList<Node>();

    public void Create(ArrayList<Kisi> kisiler) {

        Scanner giris = new Scanner(System.in);

        int temp = 1;

        for (int i = 0; i < kisiler.size(); i++) { // Tum Kisilerin Dugumunu olusturdum.            
            dugumler.add(new Node(kisiler.get(i).id, temp, 1)); // En basta tum dugumlerin x ve y degerleri 1 olarak atanıyor.
            temp++;
        }       
        
        for (int i = 0; i < dugumler.size(); i++) {

            if (dugumler.get(i).es == null) { // esi kismina deger gelmediyse.
                EsBul(dugumler.get(i), kisiler, dugumler);
            }
            
            
            if (dugumler.get(i).cocuk == null) {
                CocukBul(dugumler.get(i), kisiler, dugumler);
            }

            if (dugumler.get(i).kardes == null) {
                KardesBul(dugumler.get(i), kisiler, dugumler);
            }
            
            if (dugumler.get(i).Ukardes == null) {
                UveyKardesBul(dugumler.get(i), kisiler, dugumler);
            }
        }
        
        Node tempKisi, tempKisi2;

        for (int i = 0; i < dugumler.size(); i++) { // kardeslere anne ve babalarını bagladim.

            tempKisi = dugumler.get(i);

            while (tempKisi != null && tempKisi.kardesKontrol == 0) {

                tempKisi.kardesKontrol = 1;

                if (tempKisi.kardes != null && tempKisi.anne != null && tempKisi.baba != null) {
                    tempKisi.kardes.anne = tempKisi.anne;
                    tempKisi.kardes.baba = tempKisi.baba;
                }

                tempKisi = tempKisi.kardes;
            }
        }

        for (int i = 0; i < dugumler.size(); i++) { // kardes arrayListini doldurdum.

            tempKisi = dugumler.get(i);

            while (tempKisi.kardes != null) {

                if (dugumler.get(i).siblingsID.size() == 0) {
                    dugumler.get(i).siblingsID.add(i + 1);
                }

                dugumler.get(i).siblingsID.add(tempKisi.kardes.id);

                tempKisi = tempKisi.kardes;
            }
        }
        
        //uvey kardes
        for (int i = 0; i < dugumler.size(); i++) { // ukardeslere anne ve babalarını bagladim.

            tempKisi = dugumler.get(i);

            while (tempKisi != null && tempKisi.UkardesKontrol == 0) {

                tempKisi.UkardesKontrol = 1;

                if (tempKisi.Ukardes != null && tempKisi.anne != null && tempKisi.baba != null) {

                    if (kisiler.get(tempKisi.id - 1).anneAdi == kisiler.get(tempKisi.Ukardes.id - 1).anneAdi && kisiler.get(tempKisi.id - 1).babaAdi != kisiler.get(tempKisi.Ukardes.id - 1).babaAdi) {
                        tempKisi.Ukardes.anne = tempKisi.anne;

                    } else if (kisiler.get(tempKisi.id - 1).anneAdi != kisiler.get(tempKisi.Ukardes.id - 1).anneAdi && kisiler.get(tempKisi.id - 1).babaAdi == kisiler.get(tempKisi.Ukardes.id - 1).babaAdi) {
                        tempKisi.Ukardes.baba = tempKisi.baba;

                    }
                }

                tempKisi = tempKisi.Ukardes;
            }
        }
        for (int i = 0; i < dugumler.size(); i++) { // uvey kardeşler arraylisti

            tempKisi = dugumler.get(i);

            while (tempKisi.Ukardes != null) {

                if (dugumler.get(i).UsiblingsID.size() == 0) {
                    dugumler.get(i).UsiblingsID.add(i + 1);
                }

                dugumler.get(i).UsiblingsID.add(tempKisi.Ukardes.id);

                tempKisi = tempKisi.Ukardes;
            }
        }
//************
        for (int i = 0; i < dugumler.size(); i++) { // kisilerin cocuklarini arraylist icine attim.

            tempKisi = dugumler.get(i);

            if (tempKisi.cocuk != null) {
                tempKisi2 = tempKisi.cocuk;

                if (tempKisi2.kardes == null) {
                    tempKisi.kidsID.add(tempKisi2.id);
                }

                while (tempKisi2 != null && tempKisi2.kardes != null) {

                    tempKisi.kidsID.add(tempKisi2.id);

                    tempKisi2 = tempKisi2.kardes;

                    if (tempKisi2.kardes == null) {
                        tempKisi.kidsID.add(tempKisi2.id);
                    }
                }
            }
        }

        /*for (int i = 0; i < dugumler.size(); i++) { //Burasi kardeslerin kardes listelerini esitliyor. iliski bulmada kullanıldı.
            if(dugumler.get(i).kardes != null)
                dugumler.get(i).kardes.siblingsID = dugumler.get(i).siblingsID;
        }*/
        
        for (int i = 0; i < dugumler.size(); i++) {
            dugumler.get(i).esKontrol = 0;
            dugumler.get(i).cocukKontrol = 0;
            dugumler.get(i).kardesKontrol = 0;
        }

        assignXY(dugumler);

        int xMax = 0, tempX = 0;

        for (int i = 0; i < dugumler.size(); i++) {

            if (dugumler.get(i).kardes == null && dugumler.get(i).es == null) { // kardeslerin sonunda ise

                tempX = dugumler.get(i).x;
                xMax = (tempX > xMax) ? tempX : xMax;
            }

            if (dugumler.get(i).y == 1 && dugumler.get(i).x < xMax) { // Zeynep ve Furkan buraya giriyor    
                dugumler.get(i).x = xMax + 3;

                for (int j = i; j < dugumler.size(); j++) {

                    if (dugumler.get(j).es != null) {
                        dugumler.get(j).cocukKontrol = dugumler.get(j).es.cocukKontrol = 0;
                        dugumler.get(j).esKontrol = dugumler.get(j).es.esKontrol = 0;
                    }

                    dugumler.get(j).kardesKontrol = 0;
                }
            }
        }

        assignXY(dugumler);

        Node u;

        for (int i = 0; i < dugumler.size(); i++) {
            u = dugumler.get(i);

            if (u.kardes != null && u.kardes.x < u.x) {
                u.kardesKontrol = 0;

                for (int j = i; j < dugumler.size(); j++) {

                    if (dugumler.get(j).es != null) {
                        dugumler.get(j).cocukKontrol = dugumler.get(j).es.cocukKontrol = 0;
                        dugumler.get(j).esKontrol = dugumler.get(j).es.esKontrol = 0;
                    }

                    dugumler.get(j).kardesKontrol = 0;
                }
            }
        }

        assignXY(dugumler);

        Node t;

        for (int i = 0; i < dugumler.size(); i++) {
            dugumler.get(i).cocukKontrol = 0;
        }

        for (int i = 0; i < dugumler.size(); i++) {
            t = dugumler.get(i);

            if (t.cocuk != null && t.cocuk.es == null && t.cocukKontrol == 0) {
                t.cocuk.x = t.x + 1;
                t.cocuk.y = t.y + 2;

                t.cocukKontrol = t.es.cocukKontrol = 1;
            }
        }     
        
        String isim1, isim2;
        
        System.out.print("\nBirinci Kisinin İsmini Giriniz: ");
        isim1 = giris.nextLine();
        System.out.print("İkinci Kisinin İsmini Giriniz: ");
        isim2 = giris.nextLine();    
        
        System.out.println();
        
        iliskiBul(isim1, isim2, dugumler, kisiler);
      
        int esID = 0;

        for (int i = 0; i < dugumler.size(); i++) {
            if (dugumler.get(i).es != null && kisiler.get(i).cinsiyet.equals("")) {

                esID = dugumler.get(i).es.id;

                if (kisiler.get(esID - 1).cinsiyet.equals("Erkek")) {
                    kisiler.get(i).cinsiyet = "Kadın";
                }

                if (kisiler.get(esID - 1).cinsiyet.equals("Kadın")) {
                    kisiler.get(i).cinsiyet = "Erkek";
                }
            }
        }

       for (Kisi k : kisiler) { // dogum tarihlerini duzelttim           
            
            if(k.dogumTarihi.length() != 10)
                k.dogumTarihi = "0"+k.dogumTarihi;
            
            if(k.dogumTarihi.equals("0"))
                k.dogumTarihi = "00.00.0000";
            
            //System.out.println(k.dogumTarihi);
        }
       
        System.out.println("İsimleri Aynı Olan Kişiler ve Yaşları\n");
        
        Kisi birinciKisi = null;
        Kisi ikinciKisi = null;

        String dogumt1 = null;
        String dogumt2 = null;

        int yas1 = 0;
        int yas2 = 0;

        for (int i = 0; i < kisiler.size(); i++) {

            birinciKisi = kisiler.get(i);

            dogumt1 = birinciKisi.dogumTarihi;
            yas1 = Integer.parseInt(dogumt1.charAt(6)+""+dogumt1.charAt(7)+""+dogumt1.charAt(8)+""+dogumt1.charAt(9));
            if(yas1 != 0) yas1 = 2022 - yas1;

            for (int j = i+1; j < kisiler.size(); j++) {

                ikinciKisi = kisiler.get(j);

                dogumt2 = ikinciKisi.dogumTarihi;
                yas2 = Integer.parseInt(dogumt2.charAt(6)+""+dogumt2.charAt(7)+""+dogumt2.charAt(8)+""+dogumt2.charAt(9));
                if(yas2 != 0) yas2 = 2022 - yas2;

                ikinciKisi = kisiler.get(j);

                if(birinciKisi.ad.equalsIgnoreCase(ikinciKisi.ad))
                    System.out.println(birinciKisi.ad+" "+birinciKisi.soyad+" "+yas1+" and "+ikinciKisi.ad+" "+ikinciKisi.soyad+" "+yas2);
            }
        }
        
        int yMax = 0;
        int tempY = 0;
        
        for (int i = 0; i < dugumler.size(); i++) {
            
            tempY = dugumler.get(i).y;
            
            if(tempY > yMax) yMax = tempY;
            
        }
        
        System.out.println("\nAğacın Derinliği X ve Y değer atama fonksiyonlarında Y değerlerine\natanan değerlere göre hesaplanmıştır.\n");
        System.out.println("Ağacın Derinliği = (yMax + 1) / 2 = "+(yMax+1)/2);
        
        System.out.println("\n\nDevam Etmek İçin Enter Tuşuna Basınız.");
        giris.nextLine();
        System.out.println();
        
        BFS(kisiler);
        System.out.println("BFS BİTTİ");
        DFS(kisiler);
        System.out.println("DFS BİTTİ");
        
        ArayuzOlustur(dugumler, kisiler); // bu üstte olursa cinsiyetler eşitlenmez. Arayüzde infinity loop var.
    }

    public void alfabetikSirala(ArrayList<Integer> idList, ArrayList<Kisi> kisiler) {//3 harf kontrol ediyor
        String Ad1 = null, Ad2 = null;
        String soyAd1 = null, soyAd2 = null;
        int id1 = 0, id2 = 0;
        int temp = 0;
        if (idList.size() == 1) {
            System.out.println("Üvey olan Kardeşler:");
        }
        for (int i = 0; i < idList.size(); i++) {
            for (int j = 0; j < idList.size() - 1 - i; j++) {
                id1 = idList.get(j + 1);
                Ad1 = kisiler.get(id1 - 1).ad;
                soyAd1 = kisiler.get(id1 - 1).soyad;
                id2 = idList.get(j);
                Ad2 = kisiler.get(id2 - 1).ad;
                soyAd2 = kisiler.get(id2 - 1).soyad;

                if (!Ad1.equals(Ad2)) {//adları eşit değilse

                    if (Ad1.charAt(0) < Ad2.charAt(0)) {
                        System.out.println("----------------------------------------------");
                        System.out.println(idList.toString());
                        temp = id1;
                        System.out.println("tutulan->" + temp);
                        idList.set((j + 1), id2);
                        System.out.println(idList.toString());
                        idList.set(j, temp);
                        System.out.println(idList.toString());

                    } else if (Ad1.charAt(0) == Ad2.charAt(0)) {
                        if (Ad1.charAt(1) < Ad2.charAt(1)) {
                            System.out.println("----------------------------------------------");
                            System.out.println(idList.toString());
                            temp = id1;
                            System.out.println("tutulan->" + temp);
                            idList.set((j + 1), id2);
                            System.out.println(idList.toString());
                            idList.set(j, temp);
                            System.out.println(idList.toString());
                        } else if (Ad1.charAt(1) == Ad2.charAt(1)) {
                            if (Ad1.charAt(2) < Ad2.charAt(2)) {
                                System.out.println("----------------------------------------------");
                                System.out.println(idList.toString());
                                temp = id1;
                                System.out.println("tutulan->" + temp);
                                idList.set((j + 1), id2);
                                System.out.println(idList.toString());
                                idList.set(j, temp);
                                System.out.println(idList.toString());
                            } else if (Ad1.charAt(2) == Ad2.charAt(2)) {
                                if (Ad1.charAt(1) < Ad2.charAt(1)) {
                                    System.out.println("----------------------------------------------");
                                    System.out.println(idList.toString());
                                    temp = id1;
                                    System.out.println("tutulan->" + temp);
                                    idList.set((j + 1), id2);
                                    System.out.println(idList.toString());
                                    idList.set(j, temp);
                                    System.out.println(idList.toString());
                                } else if (Ad1.charAt(2) == Ad2.charAt(2)) {
                                    if (Ad1.charAt(3) < Ad2.charAt(3)) {
                                        System.out.println("----------------------------------------------");
                                        System.out.println(idList.toString());
                                        temp = id1;
                                        System.out.println("tutulan->" + temp);
                                        idList.set((j + 1), id2);
                                        System.out.println(idList.toString());
                                        idList.set(j, temp);
                                        System.out.println(idList.toString());
                                    } else if (Ad1.charAt(3) > Ad2.charAt(3)) {

                                        System.out.println("----------------------------------------------");
                                        System.out.println(idList.toString());
                                        temp = id1;
                                        System.out.println("tutulan->" + temp);
                                        idList.set((j + 1), temp);
                                        System.out.println(idList.toString());
                                        idList.set(j, id2);
                                        System.out.println(idList.toString());
                                    }
                                } else {
                                    System.out.println("----------------------------------------------");
                                    System.out.println(idList.toString());
                                    temp = id1;
                                    System.out.println("tutulan->" + temp);
                                    idList.set((j + 1), temp);
                                    System.out.println(idList.toString());
                                    idList.set(j, id2);
                                    System.out.println(idList.toString());
                                }
                            } else {
                                System.out.println("----------------------------------------------");
                                System.out.println(idList.toString());
                                temp = id1;
                                System.out.println("tutulan->" + temp);
                                idList.set((j + 1), temp);
                                System.out.println(idList.toString());
                                idList.set(j, id2);
                                System.out.println(idList.toString());
                            }

                        } else {
                            System.out.println("----------------------------------------------");
                            System.out.println(idList.toString());
                            temp = id1;
                            System.out.println("tutulan->" + temp);
                            idList.set((j + 1), temp);
                            System.out.println(idList.toString());
                            idList.set(j, id2);
                            System.out.println(idList.toString());

                        }
                    }
                } else {//isimler eşitse soyad kontrol ediliyor
                    if (soyAd1.charAt(0) < soyAd2.charAt(0)) {
                        System.out.println("----------------------------------------------");
                        System.out.println(idList.toString());
                        temp = id1;
                        System.out.println("tutulan->" + temp);
                        idList.set((j + 1), id2);
                        System.out.println(idList.toString());
                        idList.set(j, temp);
                        System.out.println(idList.toString());

                    } else if (soyAd1.charAt(0) == soyAd2.charAt(0)) {
                        if (soyAd1.charAt(1) < soyAd2.charAt(1)) {
                            System.out.println("----------------------------------------------");
                            System.out.println(idList.toString());
                            temp = id1;
                            System.out.println("tutulan->" + temp);
                            idList.set((j + 1), id2);
                            System.out.println(idList.toString());
                            idList.set(j, temp);
                            System.out.println(idList.toString());
                        } else if (soyAd1.charAt(1) == soyAd2.charAt(1)) {
                            if (soyAd1.charAt(2) < soyAd2.charAt(2)) {
                                System.out.println("----------------------------------------------");
                                System.out.println(idList.toString());
                                temp = id1;
                                System.out.println("tutulan->" + temp);
                                idList.set((j + 1), id2);
                                System.out.println(idList.toString());
                                idList.set(j, temp);
                                System.out.println(idList.toString());
                            } else if (soyAd1.charAt(2) == soyAd2.charAt(2)) {
                                if (soyAd1.charAt(1) < soyAd2.charAt(1)) {
                                    System.out.println("----------------------------------------------");
                                    System.out.println(idList.toString());
                                    temp = id1;
                                    System.out.println("tutulan->" + temp);
                                    idList.set((j + 1), id2);
                                    System.out.println(idList.toString());
                                    idList.set(j, temp);
                                    System.out.println(idList.toString());
                                } else if (soyAd1.charAt(2) == soyAd2.charAt(2)) {
                                    if (soyAd1.charAt(3) < soyAd2.charAt(3)) {
                                        System.out.println("----------------------------------------------");
                                        System.out.println(idList.toString());
                                        temp = id1;
                                        System.out.println("tutulan->" + temp);
                                        idList.set((j + 1), id2);
                                        System.out.println(idList.toString());
                                        idList.set(j, temp);
                                        System.out.println(idList.toString());
                                    } else if (soyAd1.charAt(3) > soyAd2.charAt(3)) {

                                        System.out.println("----------------------------------------------");
                                        System.out.println(idList.toString());
                                        temp = id1;
                                        System.out.println("tutulan->" + temp);
                                        idList.set((j + 1), temp);
                                        System.out.println(idList.toString());
                                        idList.set(j, id2);
                                        System.out.println(idList.toString());
                                    }
                                } else {
                                    System.out.println("----------------------------------------------");
                                    System.out.println(idList.toString());
                                    temp = id1;
                                    System.out.println("tutulan->" + temp);
                                    idList.set((j + 1), temp);
                                    System.out.println(idList.toString());
                                    idList.set(j, id2);
                                    System.out.println(idList.toString());
                                }
                            } else {
                                System.out.println("----------------------------------------------");
                                System.out.println(idList.toString());
                                temp = id1;
                                System.out.println("tutulan->" + temp);
                                idList.set((j + 1), temp);
                                System.out.println(idList.toString());
                                idList.set(j, id2);
                                System.out.println(idList.toString());
                            }

                        } else {
                            System.out.println("----------------------------------------------");
                            System.out.println(idList.toString());
                            temp = id1;
                            System.out.println("tutulan->" + temp);
                            idList.set((j + 1), temp);
                            System.out.println(idList.toString());
                            idList.set(j, id2);
                            System.out.println(idList.toString());

                        }
                    }
                }

            }
        }           
    }

    public void dogumTarihiSirala(ArrayList<Integer> idList, ArrayList<Kisi> kisiler) {

        String gunStr1 = null, ayStr1 = null, yilStr1 = null, tarih1 = null;
        String gunStr2 = null, ayStr2 = null, yilStr2 = null, tarih2 = null;

        int gun1 = 0, ay1 = 0, yil1 = 0, id1 = 0;
        int gun2 = 0, ay2 = 0, yil2 = 0, id2 = 0;

        int temp = 0;
        if (idList.size() == 1) {
            System.out.println("Çocuğu olmayan düğümler:");
        }
        for (int i = 0; i < idList.size(); i++) {

            for (int j = 0; j < idList.size() - 1 - i; j++) {

                id1 = idList.get(j + 1);
                tarih1 = kisiler.get(id1 - 1).dogumTarihi;

                if (tarih1.charAt(0) != '0') {
                    gunStr1 = tarih1.charAt(0) + "" + tarih1.charAt(1);
                } else {
                    gunStr1 = tarih1.charAt(1) + "";
                }

                if (tarih1.charAt(0) != '0') {
                    ayStr1 = tarih1.charAt(3) + "" + tarih1.charAt(4);
                } else {
                    ayStr1 = tarih1.charAt(4) + "";
                }

                yilStr1 = tarih1.charAt(6) + "" + tarih1.charAt(7) + "" + tarih1.charAt(8) + "" + tarih1.charAt(9);

                gun1 = Integer.parseInt(gunStr1);
                ay1 = Integer.parseInt(ayStr1);
                yil1 = Integer.parseInt(yilStr1);

                id2 = idList.get(j);
                tarih2 = kisiler.get(id2 - 1).dogumTarihi;

                if (tarih2.charAt(0) != '0') {
                    gunStr2 = tarih2.charAt(0) + "" + tarih2.charAt(1);
                } else {
                    gunStr2 = tarih2.charAt(1) + "";
                }

                if (tarih2.charAt(0) != '0') {
                    ayStr2 = tarih2.charAt(3) + "" + tarih2.charAt(4);
                } else {
                    ayStr2 = tarih2.charAt(4) + "";
                }

                yilStr2 = tarih2.charAt(6) + "" + tarih2.charAt(7) + "" + tarih2.charAt(8) + "" + tarih2.charAt(9);

                gun2 = Integer.parseInt(gunStr2);
                ay2 = Integer.parseInt(ayStr2);
                yil2 = Integer.parseInt(yilStr2);

                if (yil1 < yil2) {
                    System.out.println("----------------------------------------------");

                    System.out.println(idList.toString());

                    temp = id1;
                    System.out.println("tutulan->" + temp);
                    idList.set((j + 1), id2);
                    System.out.println(idList.toString());
                    idList.set(j, temp);
                    System.out.println(idList.toString());
                } else if (yil1 == yil2 && ay1 < ay2) {
                    System.out.println("----------------------------------------------");

                    System.out.println(idList.toString());

                    temp = id1;

                    System.out.println("tutulan->" + temp);
                    idList.set((j + 1), id2);
                    System.out.println(idList.toString());
                    idList.set(j, temp);
                    System.out.println(idList.toString());

                } else if (yil1 == yil2 && ay1 == ay2 && gun1 < gun2) {
                    System.out.println("----------------------------------------------");
                    System.out.println(idList.toString());

                    temp = id1;
                    System.out.println("tutulan->" + temp);
                    idList.set((j + 1), id2);
                    System.out.println(idList.toString());
                    idList.set(j, temp);
                    System.out.println(idList.toString());
                }
            }
        }       
    }
    
   public void altSoyGez(ArrayList<Integer> soyID, Node root, ArrayList<Node> dugumler) {

        soyID.add(root.id);
        
        if (root.es != null) {
            soyID.add(root.es.id);
        }

        if (root.cocuk == null && root.siblingsID.size() != 1) { // kendisinin cocuğu yok ama kardeslerinin var.
            for (int i : root.siblingsID) {
                if (dugumler.get(i - 1).cocuk != null) {
                    //soyID.add(i);
                    altSoyGez(soyID, dugumler.get(i - 1), dugumler);
                }
            }
        }

        if (root.cocuk != null && root.kidsID.size() != 0) {
            for (int i : root.kidsID) {
                //soyID.add(i);
                altSoyGez(soyID, dugumler.get(i - 1), dugumler);
            }
        }
    }
public void altSoyGez2(ArrayList<Integer> soyID, Node root, ArrayList<Node> dugumler) {
        
        int ilkKardes = 0;

        soyID.add(root.id);

        if (root.es != null) {
            soyID.add(root.es.id);
        }

        if(root.kardes != null){
            //soyID.add(root.kardes.id);
            altSoyGez2(soyID, root.kardes, dugumler);
        }
        
        if(root.siblingsID.size() != 0) ilkKardes = root.siblingsID.get(0);
 
        if(root.kardes == null && root.siblingsID.size() != 0 && dugumler.get(ilkKardes-1).cocuk != null){
            for(int c : root.siblingsID){ // kardeslerin cocuklarina bakacam.
                
                if(dugumler.get(c-1).cocuk != null){
                    //soyID.add(dugumler.get(c-1).cocuk.id);
                    altSoyGez2(soyID, dugumler.get(c-1).cocuk, dugumler);
                }             
            }
        }
        
        else if(root.cocuk != null){
            //soyID.add(root.cocuk.id);
            altSoyGez2(soyID, root.cocuk, dugumler);
        }
    }
 public int altSoyGez3Left(ArrayList <Integer> gezilenler, Node root, int arananID, ArrayList <Node> dugumler){
        
        gezilenler.add(root.id);        
        
        if(gezilenler.contains(arananID))
            return 0;
        
        if(root.baba != null && root.anne != null && root.baba.x < root.anne.x && root.anne.id != arananID)
            altSoyGez3Left(gezilenler, root.baba, arananID, dugumler);
        
        else if(root.baba != null && root.anne != null && root.baba.x > root.anne.x && root.baba.id != arananID)
            altSoyGez3Left(gezilenler, root.anne, arananID, dugumler);      
        
        if(root.anne != null && root.anne.id == arananID && gezilenler.contains(arananID) == false)
            gezilenler.add(root.anne.id);
        
        else if(root.anne != null && root.baba.id == arananID && gezilenler.contains(arananID) == false)
            gezilenler.add(root.baba.id);
    
        return 1;    
    }

// sonraki fonksiyon sağdan gitmek için

public int altSoyGez3Right(ArrayList <Integer> gezilenler, Node root, int arananID, ArrayList <Node> dugumler){
        
        gezilenler.add(root.id);        
        
        if(gezilenler.contains(arananID))
            return 0;
        
        if(root.baba != null && root.anne != null && root.baba.x < root.anne.x && root.anne.id != arananID)
            altSoyGez3Left(gezilenler, root.anne, arananID, dugumler);
        
        else if(root.baba != null && root.anne != null && root.baba.x > root.anne.x && root.baba.id != arananID)
            altSoyGez3Left(gezilenler, root.baba, arananID, dugumler);      
        
        if(root.anne != null && root.anne.id == arananID && gezilenler.contains(arananID) == false)
            gezilenler.add(root.anne.id);
        
        else if(root.anne != null && root.baba.id == arananID && gezilenler.contains(arananID) == false)
            gezilenler.add(root.baba.id);
    
        return 1;    
    }
    
public void altSoyGez4(String name, Node root, ArrayList <Node> dugumler, ArrayList <Kisi> kisiler){
        
        String motherName = null, fatherName = null;
        
        if(root.anne != null){
            motherName = kisiler.get(root.anne.id-1).ad+" "+kisiler.get(root.anne.id-1).soyad;
            iliskiBul(name, motherName, dugumler, kisiler);
        }
        
        if(root.baba != null){
            fatherName = kisiler.get(root.baba.id-1).ad+" "+kisiler.get(root.baba.id-1).soyad;
            iliskiBul(name, fatherName, dugumler, kisiler);
        }
        
        if(root.anne != null) altSoyGez4(name, root.anne, dugumler, kisiler);
        if(root.baba != null) altSoyGez4(name, root.baba, dugumler, kisiler);
    }     

public void iliskiBul(String isim1, String isim2, ArrayList<Node> dugumler, ArrayList<Kisi> kisiler) {

        int id1 = 0, id2 = 0, temp = 0;

        for (int i = 0; i < kisiler.size(); i++) {

            String isim = kisiler.get(i).ad + " " + kisiler.get(i).soyad;

            if (isim.equalsIgnoreCase(isim1)) {
                id1 = kisiler.get(i).id;
            }

            if (isim.equalsIgnoreCase(isim2)) {
                id2 = kisiler.get(i).id;
            }
        }

        if (dugumler.get(id1 - 1).y < dugumler.get(id2 - 1).y) {
            temp = id1;
            id1 = id2;
            id2 = temp;
        }

        if (dugumler.get(id1 - 1).y == dugumler.get(id2 - 1).y && dugumler.get(id1 - 1).x > dugumler.get(id2 - 1).x) {
            temp = id1;
            id1 = id2;
            id2 = temp;
        }

        System.out.println(kisiler.get(id1 - 1).ad + " " + kisiler.get(id1 - 1).soyad + " ile " + kisiler.get(id2 - 1).ad + " " + kisiler.get(id2 - 1).soyad + " arasındaki ilişki; ");

        ArrayList <Integer> gezilenler = new ArrayList <Integer> ();
        
        altSoyGez3Left(gezilenler, dugumler.get(id1-1), id2, dugumler); // id1 altta id2 ustte
        
        if(gezilenler.contains(id2) == false){ // burası 19 Aralık'ta yazıldı. 
            gezilenler.clear();
            altSoyGez3Right(gezilenler, dugumler.get(id1-1), id2, dugumler);
        } // Eğer ağacın soluna doğru değilse sağına doğru gidilir.
                
        System.out.println("İzlenilen yol -> "+gezilenler.toString());

        for (int i = 0; i < gezilenler.size()-1; i++) {
            
            if(dugumler.get(gezilenler.get(i)-1).baba.id == dugumler.get(gezilenler.get(i+1)-1).id)
                if(i+1 != gezilenler.size()-1) System.out.print("Babasının ");
                else System.out.print("Babası\n\n");
            
            if(dugumler.get(gezilenler.get(i)-1).anne.id == dugumler.get(gezilenler.get(i+1)-1).id)
                if(i+1 != gezilenler.size()-1) System.out.print("Annesinin ");
                else System.out.print("Annesi\n\n");
        }   
    }
public void BFS(ArrayList<Kisi> kisiler) {
        int boyut = dugumler.size();
        int sonid = dugumler.get(boyut - 1).id;
        Graph uv = new Graph(1000);

        for (int i = 1; i < sonid; i++) {
            int a = dugumler.get(i).id;

            if (dugumler.get(i).Ukardes != null) {
                for (int j = 0; j < dugumler.get(i).UsiblingsID.size(); j++) {
                    uv.addEdge(a, dugumler.get(i).UsiblingsID.get(j));
                    uv.addEdge(dugumler.get(i).UsiblingsID.get(j), a);
                }
            }

            if (dugumler.get(i).baba != null) {
                uv.addEdge(a, dugumler.get(i).baba.id);
                uv.addEdge(dugumler.get(i).baba.id, a);
            }

            if (dugumler.get(i).anne != null) {
                uv.addEdge(a, dugumler.get(i).anne.id);
                uv.addEdge(dugumler.get(i).anne.id, a);
            }
        }

        uv.BFS(1, kisiler);
    }

    public void DFS(ArrayList<Kisi> kisiler) {
        int boyut = dugumler.size();
        int sonid = dugumler.get(boyut - 1).id;
        Graph ds = new Graph(1000);

        for (int i = 1; i < sonid; i++) {
            int a = dugumler.get(i).id;

            if (dugumler.get(i).es != null) {
                ds.addEdge(a, dugumler.get(i).es.id);
                ds.addEdge(dugumler.get(i).es.id, a);

            }
            if (dugumler.get(i).cocuk != null) {

                for (int j = 0; j < dugumler.get(i).kidsID.size(); j++) {
                    ds.addEdge(a, dugumler.get(i).kidsID.get(j));
                    ds.addEdge(dugumler.get(i).kidsID.get(j), a);
                }

            }

        }
        System.out.println("DFS START");
        ds.DFS(1, kisiler);
    }

public void assignXY(ArrayList<Node> dugumler) {

        Node kisi;

        for (int i = 0; i < dugumler.size(); i++) {

            kisi = dugumler.get(i);

            if (kisi.cocuk != null && kisi.cocukKontrol == 0 && kisi.kardes != null) {
                kisi.cocukKontrol = kisi.es.cocukKontrol = 1;

                kisi.cocuk.x = kisi.x + 1;
                kisi.cocuk.y = kisi.y + 2;
            }
        }

        for (int i = 0; i < dugumler.size(); i++) {

            kisi = dugumler.get(i);

            if (kisi.kardes != null && kisi.kardesKontrol == 0) { // kardes bağlama

                kisi.kardesKontrol = 1;

                kisi.kardes.x = kisi.x + kisi.kidsID.size();
                kisi.kardes.y = kisi.y;

                for (int indis : kisi.kidsID) {
                    if (dugumler.get(indis - 1).es != null) {
                        kisi.kardes.x += 3; // Mehmet burada
                    }
                }

                if (kisi.cocuk != null && kisi.cocuk.es != null) {
                    kisi.kardes.x += kisi.cocuk.es.siblingsID.size();
                }

                if (kisi.kidsID.isEmpty()) {
                    kisi.kardes.x = kisi.x + 2;
                }

                if (kisi.es != null) {
                    kisi.kardes.x += 4;
                }

                if (kisi.cocuk != null) {

                    kisi.cocuk.x = kisi.x + 1;
                    kisi.cocuk.y = kisi.y + 2;
                }

                if (kisi.es != null && kisi.kardes.anne == null && kisi.kardes.baba == null && kisi.kardes != null) {
                    
                    kisi.kardes.x += kisi.es.siblingsID.size();

                    if (kisi.es.kardes != null) {
                        kisi.kardes.x += kisi.es.kardes.kidsID.size();

                        if (kisi.es.kardes.es != null) {
                            kisi.kardes.x += 4;
                        }
                    }
                }
            }

            if (kisi.kardes == null && kisi.cocuk != null && kisi.cocukKontrol == 0) {

                kisi.cocukKontrol = 1;
                kisi.es.cocukKontrol = 1;

                kisi.cocuk.x = kisi.x + 1;
                kisi.cocuk.y = kisi.y + 2;
            }
        }

        for (int i = 0; i < dugumler.size(); i++) {

            kisi = dugumler.get(i);

            if (kisi.es != null && kisi.esKontrol == 0) {
                kisi.esKontrol = kisi.es.esKontrol = 1;

                kisi.es.x = kisi.x + 2;
                kisi.es.y = kisi.y;
            }
        }
    }

     public void EsBul(Node kisi, ArrayList<Kisi> kisiler, ArrayList<Node> dugumler) {

        int siraKisiler = kisi.id - 1;
        int siraDugumler = 0;
        String esAdi;

        for (int i = 0; i < dugumler.size(); i++) { //herkesi gezerek esini ariyorum.

            siraDugumler = dugumler.get(i).id - 1; //id-1 bana listedeki sirasini verir.

            esAdi = kisiler.get(i).ad + " " + kisiler.get(i).soyad;

            if (kisiler.get(siraKisiler).esi.equals(esAdi)) { //bazi kisilerin isimi listede olmadığından yazmıyor.
                kisi.es = dugumler.get(siraDugumler);
                dugumler.get(siraDugumler).es = kisi;
                
                //System.out.println("Mate - Mate "+ kisiler.get(kisi.id-1).ad+" "+kisiler.get(siraDugumler).ad);
            }
        }
    }

    public void KardesBul(Node kisi, ArrayList<Kisi> kisiler, ArrayList<Node> dugumler) {

        int siraKisiler = kisi.id - 1;
        int siraDugumler = 0;
        String babaAdi, anneAdi;

        for (int i = 0; i < dugumler.size(); i++) {

            siraDugumler = dugumler.get(i).id - 1;

            babaAdi = kisiler.get(i).babaAdi;
            anneAdi = kisiler.get(i).anneAdi;

            if (babaAdi.length() != 0 && anneAdi.length() != 0) { // sonradan eklenen eslerin anne ve baba adi "" oldugu icin length=0 oluyor.
                // bu kontrol olmazsa onlari kardes saniyor
                if (babaAdi.equals(kisiler.get(siraKisiler).babaAdi) && anneAdi.equals(kisiler.get(siraKisiler).anneAdi)) {

                    if (kisiler.get(i).id != kisiler.get(siraKisiler).id && dugumler.get(siraDugumler).kardes == null) {

                        kisi.kardes = dugumler.get(siraDugumler);

                        //System.out.println("Sibling "+ kisiler.get(kisi.id-1).ad+" "+kisiler.get(siraDugumler).ad);
                        
                        break;
                    }
                }
            }
        }
    }

    public void CocukBul(Node kisi, ArrayList<Kisi> kisiler, ArrayList<Node> dugumler) {

        int siraKisiler = kisi.id - 1;
        int siraDugumler = 0, indis = 0;
        String ad, esAd, anneAdi, babaAdi;

        for (int i = 0; i < dugumler.size(); i++) {

            siraDugumler = dugumler.get(i).id - 1;
            indis = kisiler.get(siraKisiler).esi.indexOf(" ");

            if (kisiler.get(siraKisiler).esi.length() != 0) {

                ad = kisiler.get(siraKisiler).ad;
                esAd = kisiler.get(siraKisiler).esi.substring(0, indis);

                babaAdi = kisiler.get(i).babaAdi;
                anneAdi = kisiler.get(i).anneAdi;

                if (anneAdi.equals(esAd) && babaAdi.equals(ad)) {

                    if (dugumler.get(siraDugumler).kardes == null) {

                        kisi.cocuk = dugumler.get(siraDugumler);

                        dugumler.get(siraDugumler).anne = kisi.es;
                        dugumler.get(siraDugumler).baba = kisi;

                        if (kisi.es != null) {
                            kisi.es.cocuk = dugumler.get(siraDugumler);
                            
                            //System.out.println("Parent Child "+ kisiler.get(kisi.id-1).ad+" "+kisiler.get(siraDugumler).ad);
                        }
                        
                        break;
                    }
                } else if (anneAdi.equals(ad) && babaAdi.equals(esAd)) {

                    if (dugumler.get(siraDugumler).kardes == null) {

                        kisi.cocuk = dugumler.get(siraDugumler);

                        dugumler.get(siraDugumler).anne = kisi;
                        dugumler.get(siraDugumler).baba = kisi.es;

                        if (kisi.es != null) {
                            kisi.es.cocuk = dugumler.get(siraDugumler);
                            
                            //System.out.println("Parent Child "+ kisiler.get(kisi.id-1).ad+" "+kisiler.get(siraDugumler).ad);
                        }
                       
                        break;
                    }
                }
            }
        }
    }
    public void UveyKardesBul(Node kisi, ArrayList<Kisi> kisiler, ArrayList<Node> dugumler) {

        int siraKisiler = kisi.id - 1;
        int siraDugumler = 0;
        String babaAdi, anneAdi;

        for (int i = 0; i < dugumler.size(); i++) {

            siraDugumler = dugumler.get(i).id - 1;

            babaAdi = kisiler.get(i).babaAdi;
            anneAdi = kisiler.get(i).anneAdi;

            if (babaAdi.length() != 0 && anneAdi.length() != 0) {
                if (babaAdi.equals(kisiler.get(siraKisiler).babaAdi) || anneAdi.equals(kisiler.get(siraKisiler).anneAdi) && !(babaAdi.equals(kisiler.get(siraKisiler).babaAdi) && anneAdi.equals(kisiler.get(siraKisiler).anneAdi))) {

                    if (kisiler.get(i).id != kisiler.get(siraKisiler).id && dugumler.get(siraDugumler).Ukardes == null) {

                        kisi.Ukardes = dugumler.get(siraDugumler);
                        //System.out.println("" + kisiler.get(i).ad + "" + kisiler.get(siraKisiler).ad + "" + "uvey kardesler");
                        break;
                    }
                }
            }
        }
    }

        public void ArayuzOlustur(ArrayList<Node> dugumler, ArrayList<Kisi> kisiler) {

        Scanner giris = new Scanner(System.in);

        JFrame f = new JFrame();

        ArrayList<JLabel> agacDugumler = new ArrayList<JLabel>();
        ArrayList<JLabel> agacDugumler2 = new ArrayList<JLabel>();

        JLabel nodeLabel;

        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 3500, 3000);
        p1.setLayout(null);
        p1.setBackground(Color.gray);

        ArrayList<Integer> dfs = new ArrayList<Integer>();
        ArrayList<Integer> bfs = new ArrayList<Integer>();
  
        for (Node n : dugumler) {
            if (n.y == 1 && kisiler.get(n.id - 1).cinsiyet.equals("Erkek")) {
                altSoyGez(dfs, n, dugumler);
                altSoyGez2(bfs, n, dugumler);
            }
        }
        

        int x_tut;
        int y_tut;
        for (int i = 1; i < dugumler.size(); i++) {//bu tamam
            JLabel img6 = new JLabel(new ImageIcon(alt));

            if (dugumler.get(i - 1).cocuk != null && dugumler.get(i - 1).cocukKontrol == 1 && dugumler.get(i - 1).kardes != null) {
                x_tut = dugumler.get(i - 1).x;
                y_tut = dugumler.get(i - 1).y;

                if (kisiler.get(i - 1).cinsiyet.equals("Erkek")) {
                    int x_es = dugumler.get(i - 1).es.x;
                    if (x_es < x_tut) {
                        img6.setBounds(x_es * 55 + 45, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                        x_tut = dugumler.get(i - 1).x + 1;
                        y_tut = dugumler.get(i - 1).y + 2;
                    } else {
                        x_tut = dugumler.get(i - 1).x + 1;//cocuğun bulunduğu x,y
                        y_tut = dugumler.get(i - 1).y + 2;
                        img6.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);

                    }
                } else {
                    int x_es = dugumler.get(i - 1).es.x;
                    if (x_tut < x_es) {
                        x_tut = dugumler.get(i - 1).x + 1;//cocuğun bulunduğu x,y
                        y_tut = dugumler.get(i - 1).y + 2;
                        img6.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                    } else {
                        img6.setBounds(x_es * 55 + 45, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                        x_tut = dugumler.get(i - 1).x + 1;
                        y_tut = dugumler.get(i - 1).y + 2;
                    }
                }
            }
        }

        //------------------------------------------------
        for (int i = 1; i < dugumler.size(); i++) {
            y_tut = dugumler.get(i - 1).y;

            if (dugumler.get(i - 1).kardes != null && dugumler.get(i - 1).kardesKontrol == 1) {
                JLabel img6 = new JLabel(new ImageIcon(alt));
                if (kisiler.get(i - 1).cinsiyet.equals("Erkek")) {
                    x_tut = dugumler.get(i - 1).x;
                    int x_kardes = dugumler.get(i - 1).kardes.x;
                    if (x_kardes < x_tut) {
                        img6.setBounds(x_kardes * 55 + 30, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                        x_tut = dugumler.get(i - 1).x + dugumler.get(i - 1).kidsID.size();//kardeşinin konumu
                        y_tut = dugumler.get(i - 1).y;

                    } else {
                        x_tut = dugumler.get(i - 1).x + dugumler.get(i - 1).kidsID.size();//kardeşinin konumu
                        y_tut = dugumler.get(i - 1).y;
                        img6.setBounds(x_tut * 55 - 15 + 30, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                    }
                } else {
                    x_tut = dugumler.get(i - 1).x;
                    int x_kardes = dugumler.get(i - 1).kardes.x;
                    if (x_kardes < x_tut) {
                        x_tut = dugumler.get(i - 1).x + dugumler.get(i - 1).kidsID.size();//kardeşinin konumu
                        y_tut = dugumler.get(i - 1).y;
                        img6.setBounds(x_tut * 55, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                    } else {
                        img6.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                        f.add(img6);
                        img6.setVisible(true);
                        x_tut = dugumler.get(i - 1).x + dugumler.get(i - 1).kidsID.size();//kardeşinin konumu
                        y_tut = dugumler.get(i - 1).y;

                    }
                }

                for (int j : dugumler.get(i - 1).kidsID) {
                    if (dugumler.get(j - 1).es != null) {
                        x_tut = dugumler.get(i - 1).kardes.x + 3;
                    }
                }
                if (dugumler.get(i - 1).cocuk != null && dugumler.get(i - 1).es != null) {
                    if (dugumler.get(i - 1).cocuk.es != null && dugumler.get(i - 1).cocuk.es.siblingsID != null) {
                        if (kisiler.get(i - 1).cinsiyet.equals("Erkek")) {
                            x_tut = dugumler.get(i - 1).x;
                            int x_kardes = dugumler.get(i - 1).kardes.x;
                            JLabel img7 = new JLabel(new ImageIcon(alt));
                            if (x_kardes < x_tut) {
                                img7.setBounds(x_kardes * 55 + 45, y_tut * 20 - 15, 15, 15);
                                f.add(img7);
                                img7.setVisible(true);
                                x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).cocuk.es.siblingsID.size();

                            } else {
                                x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).cocuk.es.siblingsID.size();

                                img7.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                                f.add(img7);
                                img7.setVisible(true);

                            }

                        } else {
                            x_tut = dugumler.get(i - 1).x;
                            int x_kardes = dugumler.get(i - 1).kardes.x;
                            JLabel img7 = new JLabel(new ImageIcon(alt));
                            if (x_kardes < x_tut) {

                                x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).cocuk.es.siblingsID.size();

                                img7.setBounds(x_kardes * 55 + 45, y_tut * 20 - 15, 15, 15);
                                f.add(img7);
                                img7.setVisible(true);
                            } else {
                                img7.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                                f.add(img7);
                                img7.setVisible(true);
                                x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).cocuk.es.siblingsID.size();

                            }

                        }
                    }
                }

                //alt tamam
                if (dugumler.get(i - 1).kidsID.isEmpty()) {
                    if (kisiler.get(i - 1).cinsiyet.equals("Erkek")) {

                        JLabel img7 = new JLabel(new ImageIcon(duz));
                        int x_kardes = dugumler.get(i - 1).kardes.x;
                        x_tut = dugumler.get(i - 1).x;
                        if (x_kardes < x_tut) {
                            img7.setBounds(x_tut * 55, y_tut * 20 + 7, 10, 5);
                            f.add(img7);
                            img7.setVisible(true);
                            x_tut = dugumler.get(i - 1).x + 2;

                        } else {
                            x_tut = dugumler.get(i - 1).x + 2;
                            img7.setBounds(x_tut * 55 - 10, y_tut * 20 + 7, 10, 5);
                            f.add(img7);
                            img7.setVisible(true);
                        }

                    } else {

                        JLabel img7 = new JLabel(new ImageIcon(duz));
                        int x_kardes = dugumler.get(i - 1).kardes.x;
                        x_tut = dugumler.get(i - 1).x;
                        if (x_tut < x_kardes) {
                            x_tut = dugumler.get(i - 1).x + 2;
                            img7.setBounds(x_tut * 55 - 10, y_tut * 20 + 7, 10, 5);
                            f.add(img7);
                            img7.setVisible(true);

                        } else {
                            img7.setBounds(x_tut * 55, y_tut * 20 + 7, 10, 5);
                            f.add(img7);
                            img7.setVisible(true);
                            x_tut = dugumler.get(i - 1).x + 2;
                        }

                    }
                }
                //üst tamam alt tamam

                if (dugumler.get(i - 1).es != null) {
                    JLabel img7 = new JLabel(new ImageIcon(alt));

                    x_tut = dugumler.get(i - 1).kardes.x + 4;
                    img7.setBounds(x_tut * 55 - 175, y_tut * 20 - 15, 15, 15);
                    f.add(img7);
                    img7.setVisible(true);
                }
                if (dugumler.get(i - 1).cocuk != null) {
                    JLabel img7 = new JLabel(new ImageIcon(alt));

                    x_tut = dugumler.get(i - 1).x + 1;
                    y_tut = dugumler.get(i - 1).y + 2;
                    img7.setBounds(x_tut * 55 + 45, y_tut * 20 - 15, 15, 15);
                    f.add(img7);
                    img7.setVisible(true);
                }
                if (dugumler.get(i - 1).es != null && dugumler.get(i - 1).kardes.anne == null && dugumler.get(i - 1).kardes.baba == null && dugumler.get(i - 1).kardes != null) {
                    if (dugumler.get(i - 1).cocuk.es != null && dugumler.get(i - 1).cocuk.es.siblingsID != null) {

                        x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).es.siblingsID.size();
                        img6.setBounds(x_tut * 55 - 10, y_tut * 20, 15, 145);
                        f.add(img6);
                        img6.setVisible(true);

                        if (dugumler.get(i - 1).es.kardes != null) {
                            x_tut = dugumler.get(i - 1).kardes.x + dugumler.get(i - 1).es.kardes.kidsID.size();
                            img6.setBounds(x_tut * 55 - 10, y_tut * 20, 15, 15);
                            f.add(img6);
                            img6.setVisible(true);

                            if (dugumler.get(i - 1).es.kardes.es != null) {
                                x_tut = dugumler.get(i - 1).kardes.x + 4;
                                img6.setBounds(x_tut * 55 - 10, y_tut * 20, 15, 15);
                                f.add(img6);
                                img6.setVisible(true);
                            }
                        }
                    }

                }

            }

        }

        //eş bağlantısı doğru
        for (int i = 1; i < dugumler.size(); i++) {

            if (dugumler.get(i - 1).es != null && dugumler.get(i - 1).esKontrol == 1) {
                if (kisiler.get(i - 1).cinsiyet.equals("Erkek")) {

                    int x_es = dugumler.get(i - 1).es.x;
                    x_tut = dugumler.get(i - 1).x;
                    y_tut = dugumler.get(i - 1).y;
                    JLabel img6 = new JLabel(new ImageIcon(duz));

                    if (x_es < x_tut) {

                        img6.setBounds(x_es * 55 + 100, y_tut * 20 + 10, 10, 5);
                        f.add(img6);
                        img6.setVisible(true);
                        x_tut = dugumler.get(i - 1).x + 2;
                        y_tut = dugumler.get(i - 1).y;

                    } else {

                        x_tut = dugumler.get(i - 1).x + 2;
                        y_tut = dugumler.get(i - 1).y;
                        img6.setBounds(x_tut * 55 - 10, y_tut * 20 + 10, 10, 5);
                        f.add(img6);
                        img6.setVisible(true);
                    }
                }

            }
        }
for (int i = 0; i < dugumler.size(); i++) {

            nodeLabel = new JLabel(kisiler.get(i).ad + " " + kisiler.get(i).soyad + " " + Integer.toString(kisiler.get(i).id), SwingConstants.CENTER);
            nodeLabel.setForeground(Color.black);

            if (kisiler.get(i).cinsiyet.equals("Erkek")) {
                nodeLabel.setBackground(Color.blue);
            } else {
                nodeLabel.setBackground(Color.pink);
            }

            nodeLabel.setOpaque(true);

            nodeLabel.setBounds(dugumler.get(i).x * 55, dugumler.get(i).y * 20, 100, 25);
            agacDugumler.add(nodeLabel);
        }

        String kan;
        int indis;

        System.out.println("\nKan Grubu Giriniz: ");
        kan = giris.nextLine();

        String isim, isim2;
        int rootID;

        System.out.println("\nKimin alt soyunda aynı mesleği yapanları istersiniz ?: ");
        isim = giris.nextLine();

        ArrayList<Integer> soy = new ArrayList<Integer>();

        rootID = 0;

        for (int j = 0; j < dugumler.size(); j++) {

            soy.clear();

            isim2 = kisiler.get(j).ad + " " + kisiler.get(j).soyad;

            if (isim2.equalsIgnoreCase(isim)) {
                rootID = kisiler.get(j).id;
                break;
            }
        }

        altSoyGez(soy, dugumler.get(rootID - 1), dugumler);

        int s = soy.get(0) - 1;
        int temp;

        String meslek1 = kisiler.get(s).meslek;
        String meslek2;

        for (int j : soy) {

            meslek2 = kisiler.get(j - 1).meslek;

            if (!meslek1.equals(meslek2)) {
                temp = soy.indexOf(j);
                soy.set(temp, -1);
            }
        }

        System.out.println("\nAlt Soyda Aynı Meslek Listesi "+soy.toString());

        for (int i = 0; i < dugumler.size(); i++) { // kan grubu

            nodeLabel = new JLabel(kisiler.get(i).kanGrubu + " " + Integer.toString(kisiler.get(i).id), SwingConstants.CENTER);
            nodeLabel.setForeground(Color.black);

            if (kisiler.get(i).cinsiyet.equals("Erkek")) {

                nodeLabel.setBackground(Color.blue);
            } else {
                nodeLabel.setBackground(Color.pink);
            }

            if (kisiler.get(i).kanGrubu != "") {
                indis = kisiler.get(i).kanGrubu.indexOf("(");

                if (kisiler.get(i).kanGrubu.substring(0, indis).equalsIgnoreCase(kan)) {
                    nodeLabel.setBackground(Color.yellow);
                }
            }

            nodeLabel.setOpaque(true);

            nodeLabel.setBounds(dugumler.get(i).x * 55, dugumler.get(i).y * 20 + 250, 100, 25);
            agacDugumler.add(nodeLabel);
        }

        for (int i = 0; i < dugumler.size(); i++) { // aynı meslek

            nodeLabel = new JLabel(kisiler.get(i).meslek + " " + Integer.toString(kisiler.get(i).id), SwingConstants.CENTER);

            nodeLabel.setForeground(Color.black);

            if (kisiler.get(i).cinsiyet.equals("Erkek")) {
                nodeLabel.setBackground(Color.blue);
            } else {
                nodeLabel.setBackground(Color.pink);
            }

            if (soy.contains(i + 1)) {
                nodeLabel.setBackground(Color.RED);
            }

            nodeLabel.setOpaque(true);

            nodeLabel.setBounds(dugumler.get(i).x * 55, dugumler.get(i).y * 20 + 550, 100, 25);
            agacDugumler2.add(nodeLabel);
        }

        for (JLabel j : agacDugumler) {
            p1.add(j);
        }

        for (JLabel j : agacDugumler2) {
            p1.add(j);
        }

        f.add(p1);

        f.setSize(3200, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);

        System.out.println("Birinci Animasyon Listesi "+dfs.toString());
        System.out.println("İkinci Animasyon Listesi "+bfs.toString()+"\n");
 
        ArrayList <Integer> ozelSoyAgaci = new ArrayList <Integer> (); // 17 Aralık eklendi.   
        String soyRoot = null;
        String cmp;
        
        System.out.println("Kimin Soy Ağacını Görmek İstersiniz ?");
        soyRoot = giris.nextLine();
        System.out.println();
        
        for (int i = 0; i < dugumler.size(); i++) {
            cmp = kisiler.get(i).ad+" "+kisiler.get(i).soyad;
            
            if(soyRoot.equalsIgnoreCase(cmp)){
                altSoyGez(ozelSoyAgaci, dugumler.get(i), dugumler);
                altSoyGez4(cmp, dugumler.get(i), dugumler, kisiler);
            }
        }
        
        //System.out.println(ozelSoyAgaci.toString());
        
        for(int k : ozelSoyAgaci)
            agacDugumler.get(k-1).setBackground(Color.orange);
        
        System.out.println("\nDevam Etmek İçin Bir Tuşa Basınız");
        giris.nextLine();
        
        for (Kisi kisi : kisiler) {

                if (kisi.cinsiyet.equals("Erkek")) {
                    agacDugumler.get(kisi.id - 1).setBackground(Color.blue);
                } else {
                    agacDugumler.get(kisi.id - 1).setBackground(Color.pink);
                }
            }  
        
        while (true) {
            
            for(int i : dfs){
            
                agacDugumler.get(i-1).setBackground(Color.green);
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreateTree.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for(int i : bfs){
            
                agacDugumler2.get(i-1).setBackground(Color.green);
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreateTree.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (Kisi kisi : kisiler) {

                if (kisi.cinsiyet.equals("Erkek")) {
                    agacDugumler.get(kisi.id - 1).setBackground(Color.blue);
                    agacDugumler2.get(kisi.id - 1).setBackground(Color.blue);
                    } else {
                        agacDugumler.get(kisi.id - 1).setBackground(Color.pink);
                        agacDugumler2.get(kisi.id - 1).setBackground(Color.pink);                
                    }                                                                        
            }
        }
    }
}