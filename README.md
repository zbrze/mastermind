  

# Dokumentacja projektu "MasterMind"

  

Niniejszy dokument stanowi dokumentację projektu MasterMind tworzonego w ramach przedmiotu Technologie Obiektowe.

  

Autorzy: Paulina Adamczyk, Zuzanna Brzezińska, Jakub Margol, Wojciech Piechaczek

  

INSTRUKCJA URUCHOMIENIA APLIKACJI:

Aby aplikacja działała poprawnie, na komputerze musi istnieć serwer MySql (hasło administratora: *root*). Po postawieniu serwera należy stworzyć bazę o nazwoe *mastermind*.
W folderze _executable/bin/_ znajduje się plik wykonywalny _MastermindProject.bat_. Aby uruchomić program należy pobrać repozytorium a następnie dwukrotnie kliknąć lewym przyciskiem myszy na plik wykonywalny (ewentualnie z poziomu konsoli wywołać _./MastermindProject.bat_)

  

**Spis Treści**

  

1.  [Cel](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#cel)

2.  [Działanie aplikacji](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#dzia%C5%82anie-aplikacji)

3.  [Środowisko](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#srodowisko)

4.  [Diagram UML](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#diagram-uml)

5.  [Poszczególne elementy aplikacji](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#elementy-aplikacji)

- AppNavigator

- Menu

- Ekran gry

- Logowanie/Rejestracja

- Ekran statystyk

- System powiadomień

- Drag and Drop

6.  [Model danych](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#6-model-danych) 

7.   Widoki 

8.  [Przebieg pracy (details)](https://bitbucket.lab.ii.agh.edu.pl/projects/TO/repos/sr-1115-gumisie/browse/docs/docs.md?at=refs%2Fheads%2Fm2-refactor#7-przebieg-pracy)

  

## 1. Cel

  

Celem projektu jest stworzenie aplikacji umożliwiającej użytownikowi przeprowadzenie rozgrywki w grze Master Mind (polegającej na odgadnięciu w określonej liczbie tur kodu złożonego z danej liczby kolorów). Program powinien zapewniać interfejs graficzny oraz persystencję danych, takich jak użytkownicy i ich rozgrywki.

  

## 2. Działanie aplikacji

  

Po uruchomieniu aplikacji pojawia się menu, w którym użytkownik może wybrać poziom trudności gry, wejść w statystyki oraz wybrać jedną z dwóch dostępnych opcji: zalogować się lub dokonać rejestracji. W obu przypadkach na ekranie pojawia się okno logowania/rejestracji w którym użytkownik może wpisać potrzebne dane. Po autentykacji, okno to zamyka się i z powrotem jesteśmy w menu. Po naciśnięciu przycisku “start” gra rozpoczyna się.

  

Zakładamy, że zasady rozgrywki są znane.

  

Kiedy graczowi nie uda się rozwiązać zagadki, może on wrócić do menu głównego i ponownie wybrać poziom. W przeciwnym wypadku wyliczany jest wynik gracza na podstawie liczby podjętych prób odgadnięcia rozwiązania, poziomu trudności(od wyboru poziomu trudności zależy liczba dostępnych prób dopasowania wzorca oraz liczba kolorów do dyspozycji) oraz od czasu w jakim udało się ukończyć grę. W przypadku, kiedy wyliczony wynik łapie się do trzech najlepszych, do wszystkich użytkowników zarejestrowanych w grze wysyłane jest powiadomienie o zmianie leaderów w rankingu.

  

Z menu głównego istnieje również możliwość przejścia do widoku statystyk, gdzie prezentuje się 20 najlepszych graczy wraz z wynikami, jakie uzyskali.

  

![](https://lh6.googleusercontent.com/_CARcgCohdLZC6OvgFtFr1riaJVXGZxgzLLhjnRShlA0cL4hJ0VR2ndVuHJmVGp0alvu3pvBnOzCrNHQmelWxN7AVBu7khnTPtHAoG3qllLnwHEgcatGvW3BUINW_q0aZJA8Ctgs)

  

## 3. Środowisko

  

Gra będzie miała charakter gry wolnostojącej napisanej w języku java, przy pomocy frameworka Spring. Część danych będzie przechowywanych w bazie danych MySQL, z którą komunikować będziemy się za pomocą Hibernate. Za wygląd naszej aplikacji odpowiada biblioteka JavaFX, zapewniająca potrzebny interfejs graficzny.

  

## 4. Diagram UML

  

Poniżej przedstawiamy diagram klas całej aplikacji. Zgodnie zdecydowaliśmy, że na diagramie klas nie zamieścimy widoków. Będą one przedstawione na osobnych schematach.

  

![](https://lh5.googleusercontent.com/RpPUPfbLfEaarybj4SmpIRBCK4CwFAoXLrqC_F8V_gz4SsrihmmmxSqtvmTd5SzRunTI14rJPDMETArRavIh0Qiav4l40gCPDnFXUlb2zWmg-6YgsBRvHwB3_MMnHpwMdP9x94AD)

  

W następnym rozdziale zostaną omówione poszczególne części zaprezentowanego diagramu.

  

W celu ułatwienia interpretacji diagramu UML, poniżej przedstawiamy podział klas na poszczególne warstwy:

  

- dostępu danych

- logiki biznesowej

- prezentacyjną

  

![](https://lh5.googleusercontent.com/mN8mA_4iR6JRzSnfoHmmfUhuZeWCBhgsukhGui0FDvpErRu64X2C0fhfjkvwBukPUBf9IkMQU6hSkq7FL2AZcpCUwXfxU9uGWD-01PNBj9-APDcb5M29EHOjDm9gPfdI9dutkNVB)

  

## 5. Poszczególne elementy aplikacji

  

### 5.1. AppNavigator

  

![](https://lh6.googleusercontent.com/byNlaM5haA5gGHlb9k-0DQL3TpsbuN6JXH23z7g5oiC-l393ArQWIzmJk9NXtiTgVb1iakpIinnlsETOEMc4c5ds6XqasYP3r5DLZ3br9pTa17ghHmetki83i7LnIleczGAl38W9)

  

Klasa ta odpowiada za przełączanie poszczególnych okien w aplikacji w zależności od potrzeby użytkownika. Jest on powiązany z klasami:

  

- MenuController

- GamePresenter

- LoginPresenter

- RegisterPresenter

- ScoreBoardController

- MenuModel

- GameModel

- LoginModel

- RegisterModel

- ScoreBoardModel

- StatsPresenter

- StatsModel

  

Klasy typu Presenter i Controller zawierają również instancje AppNavigatora - umożliwia im to odpowiednią reakcję na dane wejściowe użytkownika.

  

### 5.2. Menu

  

Składa się na nie klasa MenuController, MenuModel oraz widok odpowiedzialny za wygląd MenuView.

  

Do interakcji widoku z modelem obiektu użyliśmy wzorca MVC, który prezentuje się następująco:

  

![](https://lh3.googleusercontent.com/iOL2m6pgelNyC6-F9ZPh488ZQ-7fR7yRH2QO30NCltxCRJisxvnP44tv-rEM4o_EQ5Dbj4EYmoCbinUoxCyNgMVjMnbN8dTviB71AfEOFX_y9juc448_hHXOjC4VfItJjRzr8Qgs)

  

Jak widać na powyższym schemacie klasa MenuController jest obserwatorem MenuView. Ten z kolei jest obserwowany i gdy nastąpi w nim zmiana informuje o tym swojego obserwatora.

  

Klasa ta posiada atrybuty user i difficulty - te dwa aspekty gry są już ustalane na poziomie menu i przekazywane dalej do prezentera gry poprzez klasę AppNavigator.

  

### 5.3. Ekran gry

  

Funkcjonalność ekranu z grą oparta jest na działaniu wzorca MVP, którego schemat zamieszczony jest poniżej:

  

![](https://lh6.googleusercontent.com/yn2rf1bst3Ko3fFfEdtzuA8q5WNLhjUSebgs3YmDJS0eLJE_D6WGDzNHHedwu3XvVEkckgDMrdtUOcJMsJLl28SRRdk3JQZOSllgpfd1pk4YNb6Ux_tpcNsd6oa2Xbvt8-tnrlwl)

  

W tym rozwiązaniu GamePresenter obserwuje widok i na tej podstawie dokonuje zmian w nim oraz w modelu gry.

  

Klasa GameModel zawiera obiekty klasy Game i GameDao - dzięki temu możliwa jest persystencja statystyk z poszczególnych rozgrywek . Dodatkowo ma atrybuty przechowujące poprawny kod, czas rozpoczęcia rozgrywki, numer rundy, aktualnie podaną przez gracza sekwencję kolorów oraz singleton ScoreComparator (odpowiadający za wybór czy i do których graczy wysłać maile).

  

### 5.4. Logowanie/Rejestracja

  

Podobnie jak w przypadku ekranu gry, w celu realizacji zarówno logowania, jak i rejestracji użytkownika postawiliśmy na wzorzec MVP (schemat analogiczny jak w podpunkcie d), z tą różnicą, że zamiast dwóch osobnych klas modelowych, oba Prezentery używają klasy UserModel i w niej dokonują zmian.

  

### 5.5. Ekran rankingu

  

Analogicznie jak w podpunkcie dotyczącym obsługi głównego menu gry, zastosowaliśmy tutaj wzorzec MVC (schemat analogiczny jak przy opisie Menu), który na bieżąco uaktualnia tabele statystyk po otrzymaniu nowego wyniku.

  

### 5.6. Ekran statystyk

  

W przypadku ekranu pokazującego statystyki zalogowanego gracza wykorzystywany jest wzorzec MVP - klasa StatsPresenter jest odpowiedzialna za obsługę tego widoku i na podstawie klasy StatsModel prezentuje dane o tym w ilu grach użytkownik brał udział i jakie jest jego ratio przegranych do wygranych.

  

### 5.7. System powiadomień

  

Aby zrealizować system powiadomień, który wysłałby maile z informacją o zmianie 3 najlepszych wyników w rankingu do wszystkich graczy, użyliśmy klasy ScoreComparator. Jej zadaniem jest informowanie klasy mailService o konieczności wysyłania powiadomień do użytkowników aplikacji, gdy zajdzie taka potrzeba. O tym czy mail ma zostać wysłany, decyduje na podstawie wyniku wyliczonego w klasie Score, na podstawie przebiegu rozgrywki. Założyliśmy, że na końcowy wynik gracza składa się liczba prób, w jakiej udało mu się rozwiązać problem, poziom trudności oraz uzyskany czas.

  

### 5.8. Drag and Drop

  

Główny ekran gry jest kontrolowany przez klasę GamePresenter oraz widok GameView.

  

GamePresenter na podstawie dostarczonej konfiguracji produkuje odpowiednią dla danego

  

poziomu trudności planszę gry, wykorzystując do tego klasę SlotRowView, która odpowiada

  

za stworzenie pojedynczego wiersza miejsc przeznaczonych na kulki, oraz ColorDragView odpowiadającą za stworzenie osobnych kulek.

  

Klasa ColorSlotView odpowiada za wygląd otworu na kulkę i jego zachowanie, w szczególności za przechwycenie upuszczonej do niego kulki (o ile nie jest zablokowany).

  

Klasa ColorDragView odpowiada za wygląd kulki oraz umożliwia jej podniesienie i przesunięcie.

  

Klasa SlotRowView tworzy rząd z podanej ilości miejsc (ColorSlotView) oraz dodaje do niego wskaźnik dla ilości poprawnych lub częściowo poprawnych ustawień. Layout ekranu gry prezentuje się następująco:

  

![](https://lh5.googleusercontent.com/_vrFk6jGkhnDGT0r9yZFBQUEeBKBAQ1wA7_VGVDCosqrcqLPrIIRIMzw4gtdn_AHJ3Yr3TjxRqpUPJx20zLMvjW2vGF-7D40COsmQhNj_5u1Kc6BR4swGVd6caMcqvn3L8sOpwN2)

  

## 6. Baza danych

  

Schemat bazy danych prezentuje się następująco:

  

![](https://lh4.googleusercontent.com/ufXctJL_beOr-bXzyYJQbPlMBN4K6DOW1G6e-rhNFuRVeeZEE4sAJ427yereeUJ4eBGAhwqdaU7SA-zEdz9agqjO1gVGXlLKa2dH1dFkKRzhYv-ajfSKtjUTtw-V2SbUr-zzNrIK)

  

Jak można zobaczyć na zamieszczonym powyżej schemacie, tabela User jest w relacji jeden do wielu z tabelą RecordedGames, co oznacza, że jeden użytkownik może posiadać wiele różnych odbytych gier.

  

Aby rozróżnić elementy bazy danych od modelu postanowiliśmy użyć jednego z wzorców persystencji, a dokładniej Object-Relational Mapping.

  

![](https://lh6.googleusercontent.com/yAR4EPY7c1kfU0f-8TpWeLsf8vYofvtd8o1CPqB2q_Jn29YoBiJCd68qBh625x0jDu4woc3O35hmh21cxUTq1tUoR6vIgK6uz0_49Imr73lE2mNYhMAng8N2cgCdoYgB6OP-48V1)

  

W modelu tym na samej górze hierarchii znajduje się klasa GenericDao zawierająca metody update, save oraz CurrentSession, gdzie dwie pierwsze odpowiadają za modyfikację bazy. Rozszerzają ją dwie klasy: GameDao oraz UserDao, implementujące kolejno interfejsy: IGameDao oraz IUserDao. Klasy te mają na celu realizować dodatkowe operacje na bazie danych.

  

## 6. Szczegóły implementacji

### 6.1 Persistance

  

Wewnątrz pakietu persistance znajdują się dwa pakiety

- interfaces, zawierający definicje interfejsów DAO

- hibernate, zawierający implementację interfejsów w oparciu o bibliotekę Hibernate

  

**Pakiet interfaces:**

  

**6.1.1 IUserDao**

  

Interfejs definiujący operacje na użytkownikach w bazie danych.

  

- save(User user)

zapisuje, lub modyfikuje użytkownika w bazie danych

- getByEmail(String email)

zwraca użytkownika o podanym adresie email, lub null jeśli nie ma takiego użytkownika

- getBestScoreFor(String email)

zwraca najlepszy wynik użytkownika o zadanym adresie email, lub -1 jeśli nie ma takiego użytkownika

- getWonGamesFor(String email)

zwraca ilość wygranych gier użytkownika o zadanym adresie email, lub -1 jeśli nie ma takiego użytkownika

- getTotalGamesFor(String email)

zwraca ilość wszystkich zakończonych gier użytkownika o zadanym adresie email, lub -1 jeśli nie ma takiego użytkownika

- getBestScores(int amount)

zwraca listę obiektów ScoreRecord< Long > reprezentującą

użytkowników o najwyższych wynikach, wraz z ich wynikami. Parametr amount określa ilość zwracanych najlepszych użytkowników.

- getMostWonGames(int amount)

zwraca listę obiektów ScoreRecord< Long > reprezentującą

użytkowników o najwięjszej ilości wygranych gier. Parametr amount określa ilość zwracanych najlepszych użytkowników.

  

**6.1.2 IGameDao**

  

Interfejs definiujący operacje na grach zapisanych w bazie danych.

  

- save(Game game)

zapisuje, lub modyfikuje zapisaną grę w bazie danych

- Game getById(int id)

zwraca zapisaną grę po id

  

**Pakiet hibernate:**

  

**6.1.3 GenericDao**

  

Abstrakcyjna klasa bazowa definiująca podstawowe funkcjonalności wspólne dla wszystkich implementacji Dao.

  

- save(T object)

zapisuje, lub aktualizyje dowolny mapowany obiekt w bazie danych

- getById(Class< T > clazz, I id)

zwraca dowolny, mapowany obiekt klasy Class< T > o zadanym id z bazy danych, lub null jeśli obiektu o zadanym id nie ma w bazie

  

**6.1.4 UserDao**

  

Implementacja interfejsu IUserDao z wykorzystaniem biblioteki Hibernate.

  

- klasa zapewnia implementację intefejsu IUserDao zgodną z wytycznymi w punkcie 6.1.1

  

**6.1.4 UserDao**

  

Implementacja interfejsu IGameDao z wykorzystaniem biblioteki Hibernate.

  

- klasa zapewnia implementację intefejsu IGameDao zgodną z wytycznymi w punkcie 6.1.2

  

### 6.2. Containers

  

**6.2.1 User**

  

Klasa przechowująca informacje dot. pojedynczego użytkownika.

  

- gettery

*getEmail()*, *emailProperty()*

*getLastName()*, *lastNameProperty()*

*getFirstName()*, *firstNameProperty()*

*getPasswordHash()*, *passwordHashProperty()*

*getSalt()*

- settery

*setEmail(String email)*

*setFirstName(String firstName)*

*setLastName(String lastName)*

*setPasswordHash(String passwordHash)*

*setSalt(String salt)*

-  *toString()*

**6.2.2 Game**

  

Klasa reprezentująca instancję pojedynczej gry.

  

- gettery

*getId()*, *idProperty()*

*getDifficulty()*, *difficultyProperty()*

*isWon()*, *isWonProperty()*

*getTime()*, *timeProperty()*

*getScore()*, *scoreProperty()*

- settery

*setId(int id)*

*setUser(User user)*

*setDifficulty(int difficulty)*

*setIsWon(boolean isWon)*

*setTime(long time)*

*setScore(long score)*

**6.2.3 Difficulty**

  

Klasa reprezentująca poziom trudności. Przechowuje dokładne wartości parametrów jakie powinna przyjąć gra dla każdego poziomu trudności.

  

- gettery

*getRows()*

*getColumns()*

*getColors()*

-  *toInt()*

konwertuje obiekt Difficulty na reprezentację liczbową

-  *fromInt(int intValue)*

konwertuje wartośc liczbową poziomu trudności na obiekt Difficulty

  
  

**6.2.4 Code**

  

Klasa reperezentująca rząd kolorowych kuleczek.

  

Zawiera listę obiektów klasy Color

  

-  *getColor(int i)*

zwraca kolor o indeksie i z listy kolorów

  

-  *putColor(int i, Color color)*

umieszcza dany kolor w liście kolorów pod i-tym indeksem

  

-  *equals(Object o)*

porównuje podany obiekt do kodu

  
  

**6.2.5 ColorPin**

  

Klasa łącząca wartość koloru z odpowiadajym mu obrazkiem kolorowej kulki.

  

- gettery

*getColor()*

*getPin()*

  

### 6.3. Models

  

**6.3.1 UserModel**

  

Klasa zawierająca dane, które mają zostać wyświetlone na ekranach związanych z autentykacją użytkownika, oraz sterująca przepływem danych na ekranach rejestracji/logowania.

  

- gettery:

*getUser()*, *getUserObjectProperty()*

*userExistsProperty()*

*wrongPasswordProperty()*

*usernameProperty()*

-  *findUserByMail(String email)*

zwraca obiekt klasy User na podstawie podanego maila (przy użyciu metody getByEmail z IUserDao)

  

-  *setNull()*

Ustawia wszystkie atrybuty obiektu UserModel na null/false i tworzy nowy obiekt

  

-  *showRegisterDialog()*

Wywołuje metodę AppNavigatora w celu otwarcia okna rejestracji

  

-  *authenticateUser(String email, String password)*

Autentykuje użytkownika po logowaniu - sprawdza czy podane dane istnieją w bazie i są poprawne oraz ustawia aktualnego użytkownika

-  *createUser(String email, String firstName, String lastName, String plainPassword)*

Jeśli użytkownik nie istnieje w bazie, tworzy instancję obiektu User i zapisuje w bazie oraz ustawia aktualnego użytkownika

  
  

**6.3.2 MenuModel**

  

Klasa zawierająca dane, które mają zostać wyświetlone na ekranie menu, oraz sterująca przepływem danych z głównego menu.

  

- gettery:

*getUser()*, *userProperty()*

*getDifficulty()*

- settery:

*setUser(User user)*

*setDifficulty(Difficulty difficulty)*

-  *startGame()*

Uruchamia metodę rozpoczynającą nową grę

-  *showScoreboard()*

Uruchamia metodę otwierającą okno rankingu

-  *register()*

Tworzy instancję obiektu klasy UserModel i wywołuje metodę otwierającą okno rejestracji

-  *login()*

Działa analogicznie do metody register(), tylko wywołuje metodę showLoginDialog

-  *askForLogin()*

Wywoływana w metodzie *startGamePressed* jeśli aktualnie nie ma żadnego zalogowanego gracza, działa jak *login* i *register*, ale wywołuje metodę pokazującą okno wyboru między logowaniem a rejestracją

  
  
  

**6.3.3 GameModel**

  

Klasa zawierająca dane, które mają zostać wyświetlone na ekranie gry, oraz zawierająca logikę gry.

  

- gettery:

*getDifficulty()*

-  *generateCode(int slotsNum)*

Losuje określoną liczbę kolorów i na ich podstawie tworzy obiekt klasy Code

-  *makeMove(Code code, int row)*

Sprawdza czy kod jest poprawny i jeśli tak, to rozpoczyna nową grę

-  *isCodeCorrect(Code submittedCode)*

Sprawdza czy podany przez gracza kod jest taki jak ten wylosowany na początku rozgrywki

-  *calculateScore()*

Oblicza wartość wyniku gracza po wygranej grze, na podstawie uzyskanego czasu gry, poziomu oraz rundy, w kórej gracz odgadł sekwencję. Wzór na wynik wygląda następująco: score = time * difficultyLevel / round

-  *calculateTime()*

Oblicza czas w jakim użytkowanik zakończył grę. Czas podany jest w nanosekundach, dlatego należy go podzielić przez 10e9

-  *createGame(boolean isWon)*

Tworzy instancję gry, którą następnie zapisuje do bazy danych za pomocą GameDAO

-  *restart()*

Restartuje grę. Poziom oraz zalogowany użtkowanik pozostaje bez zmian

- *getCodeCorrectness(Code submittedCode)*

Zwraca liczbę częściowo i w pełni poprawnych elementów kodu wysłanego przez użytkownika

### 6.4. Presenters and Controllers

  

**6.4.1 MenuController**

  

Kontroler odpowiedzialny za wyświetlanie MenuModelu.

Steruje zdarzeniami wysyłanym przez UI ekranu menu.

  

- init(MenuModel model)

inicjalizuje widok zadanym modelem

- startGamePressed()

metoda wywoływana po naciśnięciu przycisku start. (uruchamia grę, lub prosi o zalogowanie)

- scoreBoardPressed()

metoda wywoływana po naciśnięciu przycisku najlepszych wyników

- statsPressed()

metoda wywoływana po naciśnięciu przycisku statystyk

- loginPressed()

metoda wywoływana po naciśnięciu przycisku logowania

- registerPressed()

metoda wywoływana po naciśnięciu przycisku rejestracji

- changeDifficulty()

metoda wywoływana po zmianie trudności

- logoutPressed()

metoda wywoływana po naciśnięciu przycisku wylogowania

- setWelcomeUser(User user)

wyświetla tekst powitalny dla zadanego użytkownika na ekranie menu

  

**6.4.2 LoginPresenter**

  

Prezenter odpowiedzialny za wyświetlanie UserModelu na ekranie logowania.

Steruje zdarzeniami wysyłanym przez UI ekranu logowania.

  

- init(UserModel model)

inicjalizuje widok zadanym modelem

- setDialogStage(Stage dialogStage)

ustawia scenę FXML, na której wyświetlane będzie okno dialogowe logowania

- handleLoginAction()

metoda wywoływana po naciśnięciu przycisku zalogowania

  

**6.4.3 RegisterPresenter**

  

Prezenter odpowiedzialny za wyświetlanie UserModelu na ekranie rejestracji.

Steruje zdarzeniami wysyłanym przez UI ekranu rejestracji.

  

- init(UserModel model)

inicjalizuje widok zadanym modelem

- setDialogStage(Stage dialogStage)

ustawia scenę FXML, na której wyświetlane będzie okno dialogowe rejestracji

- handleRegisterAction()

metoda wywoływana po naciśnięciu przycisku rejestracji

  
  

**6.4.4 GamePresenter**

  

Prezenter odpowiedzialny za wyświetlanie GameModelu na ekranie gry.

Steruje zdarzeniami wysyłanym przez UI ekranu gry.

  

- init(GameModel model)

inicjalizuje widok zadanym modelem

- nextRow(int rows, int columns, int fullyCorrect, int partiallyCorrect)

przechodzi do następnego rzędu w grze. rows oznacza numer obecnego wiersza, fullyCorect ilość kulek o właściwym kolorze na właściwym miejscu, a partiallyCorrect ilośc kulek o właściwym kolorze, ale na błędnym miejscu

- restart()

metoda wywoływana po naciśnięciu przycisku restartu gry

- submitCode(int rows, int columns)

metoda wywoływana po zatwierdzeniu obecnego rzędu przez użytkownika (przyciskiem 'next')

  

### 6.5. Service

  

**6.5.1 AppNavigaor**

  

Klasa odpowiedzialna za uruchamanie kolejnych widoków i przechodzenie z widoku do widoku.

  

- showMenu(MenuModel menuModel)

przełącza użytkownika na widok menu (sterowany przez zadany model)

- showRegisterDialog(UserModel userModel)

wyświetla okno dialogowe z ekranem rejestracji (sterowanym przez zadany model)

- showLoginDialog(UserModel userModel)

wyświetla okno dialogowe z ekranem logoweania (sterowanym przez zadany model)

- showLoginOrRegister(UserModel userModel)

wyświetla okno dialogowe pytające użytkownika, czy ten chce się zalogować, czy zarejestrować. Po dokonaniu przez użytkownika wyboru wyświetlane jest odpowiednie okno dialogowe (sterowane przez zadanny model)

- startNewGame(GameModel gameModel)

przełącza użytkownika na widok gry (sterowany przez zadany model)

  

**6.5.2 PasswordService**

  

Klasa pomocnicza zawierająca statyczne metody służące do generowania i sprawdzania haseł.

  

- generateSalt()

zwraca String reprezentujący kryptograficznie-losowo wygenerowaną sól

- hash(String password, String salt)

zwraca String będący kryptograficznie bezpiecznym hashem zadanego hasła z zadaną solą

- authenticate(String password, String salt, String expectedHash)

zwraca true jeśli podane hasło i sól są zgodne z zapisanym hashem 'expectedHash'

  

### 6.6. View

  

**6.6.1 ColorDragView**

  

Własny widok rozszerzający JavaFx'owy ImageView, reprezentujący obrazek, który można przeciągać i umieszczać w ColorSlotView

  

- setPin(Color color, Image pin)

ustawia obrazek, który ma wyć wyświetlany jako możliwy do przeciągnięcia, wraz z zodpowiadającą mu wartością koloru

- gettery:

*getColor()*  *getPin()*

  

**6.6.2 ColorSlotView**

  

Własny widok rozszerzający JavaFx'owy AnchorPane, reprezentujący miejse, do którego można przeciągnąć kolorową kulkę.

  

- setBackground(Image regular, Image highlighted, Image disabled)

ustawia obrazy, które będą wyświetlane jako tło kolejno: w stanie spoczynku, kiedy użytkownik przciąga kulkę nad otwór, oraz kiedy otwór zostaje zablokowany

- getColor()

zwraca kolor kulki która została umieszczona w tym otworze, lub null jeśli otwór jest pusty

  

**6.6.3 SlotRowView**

  

Własny widok rozszerzający JavaFx'owy GridPane, reprezentujący rząd otworów na kulki, wraz z czarno-białymi indykatorami.

  

- setSlotImage(Image slotImage, Image highlightedImage, Image disabledImage)

ustawia obrazy tła dla kążdego z otworów w tym rzędzie, kolejno: kiedy otwór jest w stanie spoczynku, kiedy użytkownik przciąga kulkę nad otwór, oraz kiedy otwór zostaje zablokowany

- setIndicatorImages(Image emptyImage, Image fullyCorrectImage, Image partiallyCorrectImage)

ustawaia obrazy które mają być użyte jako indykatory kolejno: kiedy nie ma żadnego indykatora, gdy użytkowik poprawnie zgadł kolor i miejsce, oraz kiedy użytkownik zgadł popranie tylko kolor

- setCorrect(int fullyCorrect, int partiallyCorrect)

ustawia indykatory dla tego rzędu zgodnie z podanymi ilościami: poprawnie odgadniętych kolorów i miejsc, oraz poprwnie odgadniętych kolorów na błędnych miejscach

- setColorCount(int colorCount)

usatwia ilość kolorów jakie ma móc pomieścić ten rząd. Innymi słowy ustawia szerokość rzędu

- isFullyFilled()

zwraca true, jeśli wszystkie pozycje w rzędzie są wypełnione kolorami

- getCode()

zwraca obiekt Code reprezentujący listę kolorów wyświetlanych w tym rzędzie

- getColorCount()

zwraca ilość miejsc na kolory w rzędzie (szerokość rzędu)

  

### 6.7. Config

  

**6.8.1 DatabaseConfig**

  

Jest to klasa sterująca wstrzykiwaniem zależności dla elementów warstwy bazy danych.

  

Wszystkie metody tej klasy służą jedynie inicjalizacji obiektów na potrzeby DI, stąd pominięte zostaną ich opisy.

  

**6.8.2 DatabaseConfig**

  

Jest to klasa sterująca wstrzykiwaniem zależności na potrzeby klasy AppNavigator.

  

Wszystkie metody tej klasy służą jedynie inicjalizacji obiektów na potrzeby DI, stąd pominięte zostaną ich opisy.

  

### 6.8. Pozostałe

  

**6.8.1 Main**

  

Punkt startowy aplikacji frameworka Spring

  

- main(String[] args)

punkt startowy aplikacji. Uruchamia aplikację JavaFX'ową

  

**6.8.2 JavaFXLauncher**

  

Klasa uruchamiająca aplikację JavaFx'ową rozszerzająca JavaFx'ową klasę Application. Ze względów na specyfikę bibliotek Spring i JavFx, wymagane są dwie osobne klasy rozruchowe Main i JavaFXLauncher.

  

- start(Stage primaryStage)

metoda wywoływana przez bibliotekę JavaFX na starcie aplikacji. Uruchamia właściwy kod aplikacji, dokonując przy tym Dependency Injection

- stop()

metoda wywoływana przez bibliotekę JavaFX w trakcie wyłącznia aplikacji. Dokonuje niezbędnych prac sprzątających

## 7.Widoki
Widoki tworzone są przy pomocy JavaFX i obsługiwane przy pomocy odpowiadającego sobie prezentera/kontrolera.


**Widok Menu:**

**![](https://lh6.googleusercontent.com/vrxWnLiplD4njUTubdb-edSvfr-f2sN-VSPT3D5XCQx_4giYpMDa2rSYVzw6kINyERUjceH5H0b7He0DZTRmLA_t2h-ZB0QwcWsZaX7CTp3LonuMJGMGAcmgkUhmXtelMYatS6OY)**

**Widok ekranu rejestracji:**

**![](https://lh4.googleusercontent.com/NlvIbAgX7pWL-cWljY0dyfMWvI5QgDVZenyLBTAbxDBWHFH48tLqP9vigpsdBhZi5Q9R-Jgjv6J0kmFwnvJGx-JIXi5ACL6dA1xg623NIzbWkHcjADBVJ6nZ0TmxFDOB8qsM-j2n)**

**Widok ekranu logowania:**
**![](https://lh3.googleusercontent.com/RBWb3IE1LivDbn4NfA5ba0WG3AxQpJTnGjsHPsVrtYtxsT4q082gZ9HMKDCYYQ_eMR1dejARNMMvmtLvrWY8JkCdcS0ceX7WS815abF63m_DLWKEiSQYsp1IoPEYBvOuaiCf5xqA)**


**Widok okna gry:**

Widok okna gry skaluje się na podstawie obecnie ustawionej trudności.

**![](https://lh4.googleusercontent.com/uMLKlKrAlZOorwGT4l6wctzCivBbj3KmENsh2umZoSsPmI0Si5SyNYzI6YQU3aAfLYwebWS9C78fxgxG2oAeNogphN7sA0n8sQLRbhvqG63yaSZ3wZ87Bic1FRkXM9ebEVBLgsCL)**

## 8. Przebieg prac

**Milestone 1:**

  

- Paulina Adamczyk: dokumentacja, architektura projektu, wybór technologii

- Zuzanna Brzezińska: diagram klas, dokumentacja, architektura projektu, wybór technologii

- Jakub Margol: konfigurowalny widok gry, architektura projektu, wybór technologii

- Wojciech Piechaczek: diagram klas, klasy widoków drag and drop, architektura projektu, wybór technologii

  

**Milestone 2:**

  

- Paulina Adamczyk: dokumentacja, Klasa GameModel, otwieranie widoku gry przy pomocy AppNavigatora, tworzenie instancji zakończonej gry w bazie danych

- Zuzanna Brzezińska: dokumentacja, prezenterzy widoku logowania i rejestracji, kontroler menu, sprawdzanie poprawności kodu użytkownika

- Jakub Margol: dokumentacja, warstwa prezentacyjna: widoki oraz ich wygląd, częściowo prezenterzy/kontrolery

- Wojciech Piechaczek: dokumentacja, warstwa persystencji, mechanizm hashowania haseł, restrukturyzacja kodu, aby uzyskać zgodność z diagramem i zasadami SOLID
