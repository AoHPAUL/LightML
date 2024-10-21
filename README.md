**Light ML - A shorthand markup language for HTML**  

**By Paul Craig - Updated: 21/10/2024**  

<hr>

**Overview** 

Light ML is a custom shorthand I wrote to make building websites faster. It uses an interpreter that converts LML into HTML. It supports most HTML elements like divs, forms, links, images, navs and much more. Each element can include attributes (e.g., id, class) and modifiers for text styling (e.g., bold, italics). This started of as a small side project but after some updating and playing with the project I think it could be a great tool for web developers.

**Key Features** 
- Supports shorthand input for adding CSS links, JavaScript files, and raw HTML. 
- Handles variables which allows for commonly used elements to be stored and reused easily.
- Supports attributes for HTML tags (e.g., id, class), and these can be included inside parentheses. If combined with variables this is quite powerful.

**Bootshorts**

I created a shorthand for commonly used bootstrap components, I have gave these the name of bootshorts. Here are some of the implemented bootshorts that exist so far:
- Navigation
- Hero Section
- Carousel
- Gallerys
- Buttons
- Alerts

These bootshort components allow users to rapidly build responsive web pages and should increase productivity when building a website.

**General syntax format**
```LightML
Element.modifiers(attributes) = content

or

Element(attributes){
    Element.modifiers(attributes) = content
}
```
- **element**: The HTML tag you want to generate (e.g., p, div, img, a, etc.). 
- **modifiers**: Optional text modifiers like bold, italics, strong (for paragraphs). 
- **attributes**: Optional attributes enclosed in parentheses (e.g., id='example', class='myClass'). 
- **content**: The inner content to be placed inside the element, enclosed in single quotes ('). For some tags the content will sit between the closing and opening tags for others it appends inside the tag itself.
- **brackets**: I use curly brackets to handle the opening and closing of nested elements. This allows for commonly nested elements to be nested inside each other.
<hr>

**Bootshort Examples**
```LightML
bs-alert-primary = This is an alert

or

bs-nav-dark{
    logo = images/logo.png
    text = my web page
    link = home, home.html
    link = about us, about.html
}
```
These 2 bootshorts above are used to create an alert or a boostrap navigation bar with links, logo and page name.

![Screenshot 2024-10-21 193829](https://github.com/user-attachments/assets/0aa1afff-276c-4551-a0d3-ebbfe828fb7f)
The above screenshot shows a simple gallery page being built in the LML editor. A couple of things to note are:
- Variables: variables are used to store anything that is repeated in the document. This can be anything from a file path to the attributes.
- Bootstrap: the word bootstrap is used to import both the css and js into the output html document.
- Bootshorts: some boosthorts can be seen in this screenshort. bs-nav is used to create a bootstrap navigation, bs-gallery is used to create a bootstrap gallery.
- Comments: comments can be added to the document with // these lines are added into the html document as html comment tags.
