import java.io.*;
import java.nio.file.FileSystems;

public class EarthquakeNotification {
	public static void main(String[] args) throws IOException {
		BufferedReader watcherFile , earthquakeFile;
		FileDataLinkedListWatchers<String> watcherFileRead = new FileDataLinkedListWatchers<>();
		FileDataLinkedListEq<String> earthquakeFileRead = new FileDataLinkedListEq();

		int option = 0;
			if (!args[0].equals("-all")) {
				watcherFile = new BufferedReader(new FileReader(String.valueOf(FileSystems.getDefault().getPath(args[0]).toAbsolutePath()))); //"src/3-watcher-file"
				earthquakeFile = new BufferedReader(new FileReader(String.valueOf(FileSystems.getDefault().getPath(args[1]).toAbsolutePath()))); //"src/3-earthquake-file"
			}else {
				option = 1;
				watcherFile = new BufferedReader(new FileReader(String.valueOf(FileSystems.getDefault().getPath(args[1]).toAbsolutePath()))); //"src/3-watcher-file"
				earthquakeFile = new BufferedReader(new FileReader(String.valueOf(FileSystems.getDefault().getPath(args[2]).toAbsolutePath())));
		 }

		ArrayList<String> magnitude = new ArrayList<>();
		DoublyLinkedList<String> watchersList = new DoublyLinkedList<>(magnitude);
		LinkedQueue<String> earthquakeQueue = new LinkedQueue<>(magnitude, watchersList);

		double time = 0;
		String watcher = "";
		String eq = "";
		String[] allInfo, watcherInfo;

		while (watcherFile.ready() || earthquakeFile.ready()) {
			if (watcherFile.ready()) {
				watcher = watcherFile.readLine();
				if (!watcher.equals("")) {
					watcherInfo = watcher.split(" ");
					if(watcherInfo[1].equals("add"))
					   watcherFileRead.addLast(Double.parseDouble(watcherInfo[0]), watcherInfo[1], watcherInfo[2], watcherInfo[3],watcherInfo[4]);
					else if (watcherInfo[1].equals("delete"))
						watcherFileRead.addLast(Double.parseDouble(watcherInfo[0]), watcherInfo[1], watcherInfo[2], "","");
					else
						watcherFileRead.addLast(Double.parseDouble(watcherInfo[0]), watcherInfo[1], "", "","");
				}
			}

			if (earthquakeFile.ready()) {
				eq = earthquakeFile.readLine();
				if (!eq.equals("")) {
					if (eq.equals("<earthquake>")) eq = earthquakeFile.readLine();

					allInfo = new String[5];
					String[] earthquakeInfo = eq.split("> ");
					int i = 0;
					while (!eq.contains("/earthquake") && allInfo[4] == null) {
						allInfo[i++] = earthquakeInfo[1].substring(0, earthquakeInfo[1].indexOf(" <"));
						earthquakeInfo = earthquakeFile.readLine().split("> ");
					}
					earthquakeFileRead.addLast(allInfo[0], allInfo[1], allInfo[2], allInfo[3], allInfo[4], 0);
					earthquakeFile.readLine();
				}
			}
		}


		while (!watcherFileRead.isEmpty() || !earthquakeFileRead.isEmpty()) {

			if(!watcherFileRead.isEmpty()) {
				if(time == Double.parseDouble(watcherFileRead.getFirstData()[0])) {
					watcherInfo = watcherFileRead.getFirstData();
					if (watcherInfo[1].equals("add")) {
						watchersList.addLast(Double.parseDouble(watcherInfo[0]), watcherInfo[2], watcherInfo[3], watcherInfo[4]);
					}else if (watcherInfo[1].equals("delete")){
						watchersList.remove(watchersList.find(watcherInfo[2]));
					}else {
						if(!earthquakeQueue.isEmpty()) earthquakeQueue.checkTimestamp(time+"");
						watchersList.queryLargest();
					}

					watcherFileRead.removeFirst();
				}
			}

			if(!earthquakeFileRead.isEmpty()) {
				if(time == Double.parseDouble(earthquakeFileRead.getFirstData()[1])) {
					allInfo = earthquakeFileRead.getFirstData();
					earthquakeQueue.enqueue(allInfo[0], allInfo[1], allInfo[2], allInfo[3], allInfo[4], option);
					earthquakeFileRead.removeFirst();
				}
			}

			System.out.println("");
			  time++;
		}
	}

}