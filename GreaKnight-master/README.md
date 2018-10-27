# Program # X
Name:  
Cosc 5730 or 4730

Description:  (how to run the program, phone/emulator screen size, android version ie 7.0)

Anything that doesn't work:


* Query working: YES.
* Create working: YES
* Update working: YES.
* Delete working: YES.


# Your grade: 50/50

# Problem 1:

I couldn't make your program work with your provider, I tested all your functions with a differnt program 4.
Also, I saw this exception several times when I performed any action:

    java.lang.Exception
        at android.database.sqlite.SQLiteCursor.getColumnIndex(SQLiteCursor.java:179)
        at android.database.AbstractCursor.getColumnIndexOrThrow(AbstractCursor.java:331)
        at android.database.CursorWrapper.getColumnIndexOrThrow(CursorWrapper.java:87)
        at android.support.v4.widget.SimpleCursorAdapter.findColumns(SimpleCursorAdapter.java:317)
        at android.support.v4.widget.SimpleCursorAdapter.<init>(SimpleCursorAdapter.java:92)
        at edu.cs4730.progject4.TransFrag.drawList(TransFrag.java:126)
        at edu.cs4730.progject4.TransFrag.onCreateView(TransFrag.java:87)



# Minor problem:

*Coding style:* name you methods with something meaningful, a method named `fixit` doesn't give a clue of what it will do.

