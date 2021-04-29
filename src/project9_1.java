import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class project9_1 {

  private String[] globalDictionary;
  private String[] personalDictionary = new String[100];
  private int nPersonal;
  private String personalFile;
  static Scanner keyboard = new Scanner(System.in);

  public project9_1(String global, String personal) {
    loadGlobalDictionary(global);
    loadPersonalDictionary(personal);
    personalFile = personal;
  }

  private void loadPersonalDictionary(String filename) {
    nPersonal = 0;
    try {
      Scanner file = new Scanner(new File(filename));
      while (file.hasNextLine()) {
        personalDictionary[nPersonal] = file.nextLine();
        nPersonal++;
      }
      file.close();
    } catch (FileNotFoundException e) {

    }
  }

  private void loadGlobalDictionary(String filename) {
    int count = 0;
    Scanner file;
    try {
      file = new Scanner(new File(filename));
      while (file.hasNextLine()) {
        count++;
        file.nextLine();
      }
      file.close();
      file = new Scanner(new File(filename));

      globalDictionary = new String[count];
      for (int i = 0; i < count; i++)
        globalDictionary[i] = file.nextLine();
      file.close();

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

  }

  private int binarySearch(String[] arr, int n, String s) {
    int first = 0, last = n - 1;
    while (first <= last) {
      int mid = (first + last) / 2;
      if (arr[mid].equalsIgnoreCase(s))
        return mid;
      else if (s.compareTo(arr[mid]) < 0)
        last = mid - 1;
      else
        first = mid + 1;
    }
    return -1;

  }

  private int linearSearch(String[] arr, int n, String s) {
    for (int i = 0; i < n; i++) {
      if (arr[i].equalsIgnoreCase(s))
        return i;
    }
    return -1;
  }

  public void checkSpelling(String word) {
    if (binarySearch(globalDictionary, globalDictionary.length, word) == -1) {
      if (linearSearch(personalDictionary, nPersonal, word) == -1) {
        System.out.println("That word is not spelled correctly");
        System.out.print("Would you like to add it to your personal dictionary Yes/No: ");
        if (keyboard.nextLine().equals("yes")) {
          if (nPersonal == personalDictionary.length)
            System.out.println("No more space to add to personal dictionary!");
          else {
            personalDictionary[nPersonal] = word;
            nPersonal++;
          }
        }
        return;
      }
    }

    System.out.println("That word is spelled correctly");
  }

  public void savePersonalDictionary() {
    try {
      PrintWriter pw = new PrintWriter(new File(personalFile));
      for (int i = 0; i < nPersonal; i++)
        pw.println(personalDictionary[i]);
      pw.close();
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    String globalFile, personalFile;
    if (args.length != 2) {
      System.out.println("Pass the global and personal dicitonary filenames as command line arguments");
      return;
    }

    globalFile = args[0];
    personalFile = args[1];

    project9_1 checker = new project9_1(globalFile, personalFile);

    boolean quit = false;

    while (!quit) {
      System.out.println("Enter a word or QUIT to stop:");
      String word = keyboard.nextLine();
      if (word.equalsIgnoreCase("QUIT"))
        quit = true;
      else
        checker.checkSpelling(word);
    }

    checker.savePersonalDictionary();
  }

}