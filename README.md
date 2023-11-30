# my_closet
My closet is an ecommerce website that sells designer shoes.

It contains the following key components.

Key components

Cloud
Firebase Authentication
Cloud Firestore
Cloud Storage


Architecture Components
ViewModel
Navigation
Hilt
Jetpack Compose
Compose Material 3
Paging
Identity One Tap
Kotlin Coroutines
Flow

Other libraries
Accompanist
Coil

Test
Junit4



Design Workflow

<img width="626" alt="Screenshot 2023-11-30 033937" src="https://github.com/captainxavier/my_closet/assets/30109625/416dade1-c4ec-4fc6-90b9-7a0844f19b10">


The database schemas is as follows

db-root 
    |
        --- users (collection)
            |
            --- $uid (document)
                |
                --- createdAt: October 4, 2023 at 2:51:18 PM UTC+3
                |
                --- displayName: "John Doe"
                |
                --- email: "johndoe@gmail.com"
                |
                --- photoUrl: "https://..."

db-root
    |
    --- banners
        |
        --- $uid(document)
            |   
            --- promotion_name:"black friday"
            |
            --- discount"25%"
            |
            --- description:"Enjoy flash sell and save up to 25%."
            |
            --- thumb:"storage-root/banners/$black_friday.png"
            |
            --- products(collection)
                |
                --- $productId: "token"

db-root
    |
    --- brands
        |
        --- $uid(document)
            |
            --- name:"nike"
            |
            --- thumb:"storage-root/brands/$nike.png"
        --- $uid(document)
            |
            --- name:"puma"
            |
            --- thumb:"storage-root/brands/$puma.png"


db-root
    |
    --- products (collection)
        |
        --- $productId (document)
            |
            --- brand: "$brandUid"
            |
            --- creationDate: October 4, 2022 at 2:51:18 PM UTC+3
            |
            --- favorites: ["uid", "uid"]
            |
            --- id: "productId"
            |
            --- image: "token"
            |
            --- name: "Nike Air Uptempo Black"
            |
            --- popular: false
            |
            --- price: 9999
            |
            --- thumb: "token"


storage-root/images/$productId.png
storage-root/thumbs/$productId.png


db-root
    |
    --- users (collection)
        |
        --- $uid (document)
            |
            --- shoppingCart (collection)
                |
                --- $shoppingCartItem (document)
                |
                --- additionDate: October 4, 2022 at 2:51:18
                |
                --- id: "productId"
                |
                --- price: 9999
                |
                --- quantity: 1
                |
                --- thumb: "token"

db-root
    |
    --- users (collection)
        |
        --- $uid (document)
            |
            --- orders (collection)
                |
                --- $orderId (document)
                |
                --- dateOfSubmission: October 4, 2022
                |
                --- location: "latlong"
                |
                --- total: 23998
                |
                --- status:processed
                |
                --- id: "productsOrderId"

db-root
    |
    --- users (collection)
        |
        --- $uid (document)
            |
            --- productsOrder (collection)
                |
                --- $productsOrderId (document)
                    |
                    --- items (array)
                        |
                        --- 0
                            |
                            --- additionDate: October 4, 2022
                            |
                            --- id: "productId"
                            |
                            --- name: "Nike Air Uptempo Black"
                            |
                            --- price: 9999
                            |
                            --- quantity: 1
                            |
                            --- thumb: "token"



In my Android Studio project structure, I've organized it into seven main packages.

1. **Di**: This package is dedicated to Dagger Hilt dependency injection. I use it to house module classes, particularly in a file called AppModule. In this file, I include objects necessary throughout the app, such as those related to FirebaseAuth, FirebaseFirestore, or FirebaseDatabase. Using the "@Inject" annotation allows me to inject instances of these classes into my repository implementation classes.

2. **Utils**: This package serves as a home for constants and utility methods applicable across the entire app. Constants refer to unchanging values, like the names of collections in Firestore and fields in documents. Utility methods include widely used functions, such as logging error messages or creating URLs pointing to images in Cloud Storage.

3. **Navigation**: This package manages classes responsible for navigating between destinations. It contains classes for each screen in the app, directions, and a separate class for the app's NavGraph.

4. **Presentation**: Also known as the UI Layer, this package has a sub-package for every screen in the app. Each sub-package includes a class for the screen, components specific to that screen, and the ViewModel class. The latter is crucial as it acts as a bridge between the UI and repository classes. Since Jetpack Compose is used, these components are represented by composable functions.

5. **Components**: This package holds all reusable composable functions across the entire app. Examples include layouts, top bars, buttons, cards, icons, and dividers.

6. **Domain**: Positioned between the UI and data layers, this package has two sub-packages. The model sub-package houses all required model classes, while the repository package contains interfaces encapsulating business logic. Actual implementations of these methods are added later to the repository implementation classes.

7. **Data**: The final package includes a repository sub-package, housing classes implementing corresponding interfaces. This involves providing implementations for each method existing in those interfaces.
8. 


UI/UX Design used 
link - https://www.behance.net/gallery/180396433/Mobile-Shoes-Shopping-App



