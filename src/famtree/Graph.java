package famtree;

import java.util.*;

public class Graph {

    private int V;
    private LinkedList<Integer> linkList[];
    ArrayList<Integer> leafNode = new ArrayList<Integer>();
    ArrayList<Integer> uvey = new ArrayList<Integer>();

    CreateTree CTgo=new CreateTree();
    FamTree FTgo=new FamTree();

    Graph(int v) {
        V = v;
        linkList = new LinkedList[v];//LinkListimizi oluşturduk
        for (int i = 0; i < v; ++i) {
            linkList[i] = new LinkedList();//her satırı link list yaptık
        }
        
    }

    public void addEdge(int v, int w) {
        linkList[v].add(w);
    }

    public void BFS(int s,ArrayList<Kisi> kisiler) {

        boolean visited[] = new boolean[V];

        LinkedList<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);
        int leafnode = 1;

        while (!queue.isEmpty()) {
            s = queue.poll();
            //System.out.print(s + " ");
            //System.out.println("\n\t\t\tSıradan kaldırılan: " + s);
            Iterator<Integer> i = linkList[s].listIterator();

            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    
                    visited[n] = true;
                    queue.add(n);
                    leafnode = 0;

                    //System.out.println("\t\t\tziyaret edilen: " + n);
                    //System.out.println("\t\t\tsıra: " + queue);
                }
            }
            if (leafnode == 0) {
                uvey.add(s);
                //System.out.println("uvey kardes"+s);
                CTgo.alfabetikSirala(uvey, kisiler);
                
            }
            
        }
    }

    public void DFSUtil(int v, boolean visited[],ArrayList<Kisi> kisiler) {
        //Gelen düğümü ziyaret edildi olarak işaretlenir
        visited[v] = true;
        //System.out.print("\nSıra: " + v);
        int leafnode = 1;
        // Buna bitişik tüm köşeler için yineleniyor
        // tepe noktası
        Iterator<Integer> i = linkList[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            //System.out.print("\t\tn:" + n);
            if (!visited[n]) {
                //System.out.print("\nziyaret edilecek: " + n);
                leafnode = 0;
                DFSUtil(n, visited,kisiler);
            }
            
        }
        if (leafnode == 1) {
            leafNode.add(v);
            //System.out.println("\nleafnode" + v);//Çocuğu olmayan düğümlerim id'si
            System.out.println("\nleafnode" + leafNode.toString());//Çocuğu olmayan düğümlerim id'si
            CTgo.dogumTarihiSirala(leafNode, kisiler);
        }
        


    }

    public void DFS(int v,ArrayList<Kisi> kisiler) {
        boolean visited[] = new boolean[V];

        DFSUtil(v, visited,kisiler);
    }

}
