# hernan-carrera-mobile-challenge
# The Guardian News App
<p> Architecture : Model - View - ViewModel  <br>
Language: Kotlin<br>

<p>This application searchs for a given term in the guardian news api service, list the results, show the item in detail, and manages a database for saving news localy</p>
Use cases:
<ul>
<li>Search news</li>
<li>Read News</li>
<li>Save news localy</li>
<li>Read saved news</li>
</ul>

Implements:<br></p>
<ul>
<li>Transition Manager for Fragments, with Bottombar and Navigation controller.</li>
<li>Gson library to handle JSON objects</li>
<li>Coil library to load Images. This library uses coroutines</li>
<li>Dagger Hilt for dependency injection</li>
<li>Room Library for persistent data. (SQLite)</li>
<li>Desing patterns like adapter, factory, singleton</li>
</ul>
<p> In this project you migth find UI implements like:<p>
<ul>
<li>Set up different styles in themes file and costumize the views</li>
<li>Create the app icon using the  Image asset creator from the android studio</li>
<li>Use custom svg on images</li>
</ul>
<p> 
This application has 3 fragments. One to list the search results, one to list the bookmarks(db newsItems), and one to show the newsDetail.<br>
The viewModels use Hilt Injection, and LiveData to comunicate events.
App layers and classes: <br> 
 -> database package. * module database. This class provides the database instance for Hilt. <br> 
                      * NewsDetailItemDao. this class provides the methods to interact with db. <br> 
                      * NewsItemDatabase. Room Database class. <br> 
 -> model. * dataclasses. This class holds the different data classes used for db, api responses and some convertion methods from one class to another. <br> 
 -> network * ResponseMapper. Get an string response from apiservice class and formats it into the correct data class<br>
            * ApiService. This class handles the api requests from the viewmodel and pass the parsed response through the callback.<br>
            * Callback. This interface is used to get the result from the apiservice in the viewmodel.<br>
 -> Utils.  * DatabaseConverters. This class converts the date into long and viceversa for db objects <br>
            * Extensions. Holds some extensions functions like validate user input for edittext.<br>
            * MessageFactory. A simple class to show msg from the app.<br>
 -> Views  -> Adapters * Holds the listview adapters and their interface for click handling in the fragment.<br>
            * Holds the main activity and fragments.<br>
 ->  ViewModel -> * NewsDetailViewmodel. This viewModel provides:  -> the News item Detail from the apiservice -> The status of the item in the local storage 
 -> saves/delete news on local db.<br>
                * NewsLocalViewModel. Provides the saved news (bookmarks) from the db.<br>
                * NewsSearchViewModel. Provides the news from the ApiService. <br>
                
  Some ScreenShots: 
  
      
          
