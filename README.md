# WordSearch

![Screeshot](https://github.com/dekunle02/WordSearch/blob/master/Screenshot_1599582752.png)
![Screeshot](https://github.com/dekunle02/WordSearch/blob/master/Screenshot_1599591952.png)
![Screeshot](https://github.com/dekunle02/WordSearch/blob/master/Screenshot_1599592004.png)
![Screeshot](https://github.com/dekunle02/WordSearch/blob/master/Screenshot_1599592018.png)

### View Classes
Custom Views are in the view package.
The BoardView class overrides the onInterceptTouchEvent method.
The BoardLetterView class extends the TextView and is responsible for displaying the text in each grid and crossing over found words by overriding the onDraw method and taking an enum for the Line Direction.

### Worker Classes
The most important class is the Board class, it helps to make the matrix of the grid, can be constructed with default words or any list of words and with any dimension. It randomly arranges the words on the matrix, and gives us access to where all the coordinates for the correct words are.
The ViewMaker helps construct a BoardView. It creates individual BoardLetterViews from information gotten from a board object and populates a boardView

### ViewModel Class
Interacts with all the logic of the app and helps keep the logic separate from the activity lifecycle. It has many LiveData objects that are being observed in the MainActivity
