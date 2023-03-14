# CodeStack
An android app dedicated to developers. App features:
1. Find Remote jobs under the Jobs Section
2. Build a resume using Resume Builder
3. Users can go through tech news to stay updated
4. There is a blog sharing section where users can share blogs and connect with other developers

# Tech Stack Used
* **Android Studio:** Android Studio is a specialized integrated development environment (IDE) designed to help developers create applications for the Android operating system. 
    It is an official development environment for Android app development, which is provided by Google, and is freely available for download.
    
* **Android XML:** Android XML (Extensible Markup Language) is a markup language that is used for designing user interfaces (UI) in Android applications.

* **Kotlin:** Kotlin is a modern, dynamically-typed programming language that is becoming increasingly popular for Android app development.

* **Google Firebase:** It is a comprehensive mobile and web application development platform provided by Google that allows developers to build,
    manage, and deploy their applications with ease. It has been used in the project for User Authentication and in the blog section, which enables users 
    to share blogs.
    
* **Retrofit**: Retrofit is a popular HTTP client library for Android that makes it easy to consume RESTful web services.With Retrofit, you can define an 
  interface that describes the HTTP API of the web service you want to consume. You can then use this interface to make HTTP requests to the 
  web service and receive responses in a strongly typed manner.Retrofit automatically handles the conversion of the request and response data to
  and from JSON or other formats.
    
* **News API:** News API is used to show tech news to the users

* **Remotive API:** API used to display all the availaible remote jobs

# Architecture Pattern Used
The app uses a combination of MVC and MVVM Architecture patterns

* **MVC Architecture:** MVC (Model-View-Controller) is a popular architecture pattern that is commonly used in Android application development.
  The MVC architecture separates the application logic into three main components: the Model, the View, and the Controller. 
  The Resume Builder Section and the blog section uses this architecture in the app
  
  1. Model: The Model represents the application's data and business logic. It encapsulates the data and provides methods to manipulate and access it. 
     In an Android application, the Model might include classes that represent the data stored in a database or web service.

  2. View: The View is the UI component of the application. It displays the data to the user and allows the user to interact with the application. 
     In an Android application, the View might include activities, fragments, and layout files.

  3. Controller: The Controller acts as a mediator between the Model and the View. It receives input from the View, updates the Model, and updates 
     the View as necessary. In an Android application, the Controller might include activities or fragments that handle user input and update the View.
     
     <img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/mvc.jpg" alt="drawing" width="400"/>

* **MVVM Architecture:** MVVM (Model-View-ViewModel) is a popular architecture pattern that is commonly used in Android application development. 
   The MVVM architecture separates the application logic into three main components: the Model, the View, and the ViewModel. 
   The tech news section and the remote jobs section use this architecture in the app
   
   1. Model: The Model represents the application's data and business logic. It encapsulates the data and provides methods to manipulate and access it. 
      In an Android application, the Model might include classes that represent the data stored in a database or web service.

   2. View: The View is the UI component of the application. It displays the data to the user and allows the user to interact with the application. 
      In an Android application, the View might include activities, fragments, and layout files.

   3. ViewModel: The ViewModel acts as a mediator between the Model and the View. It receives input from the View, updates the Model, and provides 
    data to the View as necessary. In an Android application, the ViewModel might include classes that represent the state of the View and provide 
    methods for the View to interact with the Model.
    
       <img src="https://www.linkpicture.com/q/mvvm.jpg" alt="drawing" width="400" height="400"/>
       
# Some Pseudo-Codes
Following are some pseudo codes which give an overview of how the app works:

### Create Retrofit Instance

```Kotlin
 private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()

                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
```

### Performing HTTP GET Request using Retrofit

```Kotlin
interface NewsApi {

    @GET("v2/top-headlines")
     fun getTechnologyNews(
        @Query("country")countryCode:String="in",
        @Query("category")category:String="technology",
        @Query("apiKey")apiKey: String=API_KEY
    ): Call<NewsResponse>

}

class NewsViewModel(){
     private fun getTechnologyNews(){
        newsService.getTechnologyNews("in","technology").enqueue(
            object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    newsLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    newsLiveData.postValue(null)
                    Log.e("JOB", "Failure ${t.message}")
                }
            }
        )
    }

}
```
### Uploading Data to Firebase

```Kotlin
private fun uploadDataToFirebase(imageUri: Uri, postDescription: String){
        val fileReference = storageReference.child("${System.currentTimeMillis()}" + "." + getFileExtension(imageUri))
        fileReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
            fileReference.downloadUrl.addOnSuccessListener {
                val imageUrl = it.toString()
                val reference = firebaseDatabase.getReference("Post").push()
                val postKey = reference.key
                activity?.intent?.putExtra("postKey", postKey)

                val post = Post(
                    postDescription,
                    auth.currentUser!!.uid,
                    auth.currentUser!!.email,
                    imageUrl,
                    postKey
                )
                if (postKey != null) {
                    root.child(postKey).setValue(post)
                }
                Toast.makeText(context, "Post Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Some Error Occurred: $it", Toast.LENGTH_LONG).show()
        }
    }

```
### Retrieving Data from Firebase

```Kotlin
 private fun viewPosts(){
        val reference = firebaseDatabase.getReference("Post")
        postList = ArrayList()
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postsnap in snapshot.children){
                    val post = postsnap.getValue(Post::class.java)

                    if (post != null) {
                        postList.add(post)
                    }

                }
                postsAdapter.postDiffer.submitList(postList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error Error Occurred! ", Toast.LENGTH_LONG).show()
            }
        })
    }

```
### Creating a PDF using iText PDF

```Kotlin
 private fun createResume() {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val currentTime = System.currentTimeMillis().toString()
        val child = "$currentTime.pdf"
        val file = File(pdfPath,child)
        val outputStream = FileOutputStream(file)

        // iText- Pdf
        val pdfWriter = PdfWriter(file)
        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        //creating PDF
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(10f, 10f, 10f, 10f)


        // Adding User Name and Email
        val name = Paragraph(resumeBinding.personName.text.toString()).setBold().setFontSize(30f).setTextAlignment(TextAlignment.CENTER)
        val email = Paragraph(resumeBinding.yourEmail.text.toString()).setBold().setFontSize(24f).setTextAlignment(TextAlignment.CENTER)    
        
        document.add(name)
        document.add(email)
   
        // Close the document
        document.close()
```

# App Screenshots

Some screenshots from the app:

### Jobs Fragment

<img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/jobs.png" width="400"/>   <img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/job_view.png" width="400"/> 

### Resume Builder Fragment

<img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/resume_builder.png" width="400"/>   <img src="https://www.linkpicture.com/q/Screenshot-5_32.png" width="400" height="700"/> 

### Tech News Fragment

<img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/tech_news.png" width="400"/>   <img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/tech_news_view.png" width="400"/> 

### Connect Fragment

<img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/connect.png" width="400"/>   <img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/comment.png" width="400"/> 

### Account Fragment

<img src="https://github.com/abhinavkr2108/CodeStack/blob/master/app/src/main/res/drawable-v24/account.png" width="400"/>  




