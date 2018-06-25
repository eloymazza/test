package grafo;
import java.util.*;

public class GrafoDirigido{
	
	protected Integer cantV;
	protected Integer cantA;
	protected List<Vertice> vertices;
	protected HashMap<Integer,LinkedList<Arista>> mapaAdyacencias; 
	
	public GrafoDirigido(){
		cantV = 0;
		cantA = 0;
		vertices = new LinkedList<Vertice>();
		mapaAdyacencias = new HashMap<Integer,LinkedList<Arista>>();
	}
	
	public  Integer getCantV(){
		return cantV;
	}
	
	public Integer cantA(){
		return cantA;
	}
	
	public void agregarVertice(Vertice nuevoVertice){
		vertices.add(nuevoVertice);
		mapaAdyacencias.put(nuevoVertice.getID(), new LinkedList<Arista>());
		cantV++;
	}

	public Vertice obtenerVertice(Integer idVert) {
		
		Iterator<Vertice> it = vertices.iterator();
		Vertice vActual;
		
		while(it.hasNext()){
			vActual = it.next();
			if(vActual.getID() == idVert){
				return vActual;
			}
		}
		
		return new Vertice();
	}


	public void agregarArista(Integer idVertOrigen,  Arista arista){	
		
		if(existeVertice(idVertOrigen) && existeVertice(arista.getDestino())){
			mapaAdyacencias.get(idVertOrigen).add(arista);
			cantA++;						
		}
		
	}
	
	public boolean existeArista(Integer idVertOrigen, Integer idVertFin) {
		
		if(existeVertice(idVertOrigen) && existeVertice(idVertFin)){
			Iterator<Arista> it = mapaAdyacencias.get(idVertOrigen).iterator();
			while(it.hasNext()){
				if(it.next().getDestino() == idVertFin){
					return true;
				}
			}
		}
		return false;
	}
	
	public LinkedList<Arista> obtenerAdyacentes(Integer idVert) {
		return mapaAdyacencias.get(idVert);
	}
	
	
	private boolean existeVertice(Integer target) {
		return mapaAdyacencias.containsKey(target);

	}

	public Iterator<Vertice> getVIterator() {
		return vertices.iterator();
	}

	
	public String toString(){
		return this.vertices.toString();
	}
	
	public static class Recorridos{
		
		static State[] estados; 
		static LinkedList<Integer> ls = new LinkedList<Integer>();
		
		public static LinkedList<Integer> DFSPath(GrafoDirigido g){
			
			ls.clear();
			llenarEstados(g.getCantV());
			Integer currentID;
			Iterator<Vertice> it = g.getVIterator();
			
			while(it.hasNext()){
				currentID = it.next().getID();
				if(estados[currentID] == State.unvisited){
					DFSPath(currentID,g);									
				}
			}
			return ls;	
		}
		
		private static void DFSPath(Integer idvert, GrafoDirigido g){
				
			ls.add(idvert);
			estados[idvert] = State.visiting;
			LinkedList<Arista> ady = g.obtenerAdyacentes(idvert);
	
			Iterator<Arista> it = ady.iterator();
			Integer destino;

			while(it.hasNext()){
				destino = ((Arista)it.next()).getDestino();
				if(estados[destino] == State.unvisited){
					DFSPath(destino,g);
				}
			}
			
			estados[idvert] = State.visited;
		}
		
		public static boolean tieneCiclo(GrafoDirigido g){
			
			ls.clear();
			llenarEstados(g.getCantV());
			return tieneCiclo(0,g);

		}
		
		private static boolean tieneCiclo(Integer idvert, GrafoDirigido g){
			
			estados[idvert] = State.visiting;
			LinkedList<Arista> adyacentes = g.obtenerAdyacentes(idvert);
			Integer destino;
			
			for (Arista arista : adyacentes) {
				destino = arista.getDestino();
				if(estados[destino] == State.visiting){
					return true;
				}
				if(estados[destino] == State.unvisited){
					if(tieneCiclo(destino,g)){
						return true;
					}
				}
			}
			estados[idvert] = State.visited;
			return false;
		}
		
		public static LinkedList<Integer> BFSPath(GrafoDirigido g, Integer idVInicial){
			
			ls.clear();
			llenarEstados(g.getCantV());
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(idVInicial);
			BFSPath(idVInicial, g, queue);
			return ls;
			
		}
		
			
		private static void BFSPath(Integer idVActual, GrafoDirigido g, Queue<Integer> queue) {
			
			estados[idVActual] = State.visited;
			ls.add(idVActual);
			while(!queue.isEmpty()){
				LinkedList<Arista> adyacentes = g.obtenerAdyacentes(queue.poll());
				for (Arista arista : adyacentes) {
					Integer adyacente = arista.getDestino();
					if(estados[adyacente] == State.unvisited){
						ls.add(adyacente);
						estados[adyacente] = State.visited;
						queue.add(adyacente);
					}
				}
			}
			
		}
		

		private static void llenarEstados(Integer cantV){
			estados = new State[cantV];
			for (int i = 0; i < cantV; i++) {
				estados[i] = State.unvisited;
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		GrafoDirigido g1 = new GrafoDirigido();
		
		Vertice v1 = new Vertice("A");
		Vertice v2 = new Vertice("B");
		Vertice v3 = new Vertice("C");
		Vertice v4 = new Vertice("D");
		Vertice v5 = new Vertice("E");
		Vertice v6 = new Vertice("F");
		Vertice v7 = new Vertice("G");
		
		
		Arista a1 = new Arista(1,1);
		Arista a2 = new Arista(2,1);
		Arista a3 = new Arista(5,1);
		Arista a4 = new Arista(3,1);
		Arista a5 = new Arista(4,1);
		Arista a6 = new Arista(1,1);

		
		g1.agregarVertice(v1);
		g1.agregarVertice(v2);
		g1.agregarVertice(v3);
		g1.agregarVertice(v4);
		g1.agregarVertice(v5);
		g1.agregarVertice(v6);
		g1.agregarVertice(v7);
		
		
		g1.agregarArista(0, a1);
		g1.agregarArista(0, a2);
		g1.agregarArista(0, a3);
		g1.agregarArista(1, a4);
		g1.agregarArista(1, a5);
		g1.agregarArista(4, a6);
		
		System.out.println(g1.toString());
		System.out.println(Recorridos.tieneCiclo(g1));
		
	}
	
}
