**Light ML Documentation** 

**A shorthand markup language for HTML**  

**By Paul Craig - Updated: 09/10/2024**  

<hr>

**Overview** 

This is a Java application that converts a custom shorthand text format into well- formatted HTML. It supports various HTML elements like paragraphs, divs, forms, links, images, and even advanced layout frameworks like Bootstrap and Tailwind. Each element can include attributes (e.g., id, class) and modifiers for text styling (e.g., bold, italics).

**Key Features** 

- Supports most common HTML elements like paragraphs (p), divs (div), links (a), forms (form), and images (img). I will expand this in the future. 
- Allows the use of text modifiers such as bold, italics, and strong. 
- Supports shorthand input for adding CSS links, JavaScript files, and raw HTML. 
- Handles layout frameworks like Bootstrap and Tailwind with simple shorthand commands. 
- Supports attributes for HTML tags (e.g., id, class), and these can be included inside parentheses. 

**Variable & functional Support**
- I recently added support for variables in the form of ($variable = value). This will be expanded with functions, if/else & loops

**General syntax format**
```LightML
Element.modifiers(attributes) = 'content'
```
- **element**: The HTML tag you want to generate (e.g., p, div, img, a, etc.). 
- **modifiers**: Optional text modifiers like bold, italics, strong (for paragraphs). 
- **attributes**: Optional attributes enclosed in parentheses (e.g., id='example', class='myClass'). 
- **content**: The inner content to be placed inside the element, enclosed in single quotes ('). For some tags the content will sit between the closing and opening tags for others it appends inside the tag itself. 
<hr>
**Supported Elements** 

Here’s a breakdown of the supported elements with their LightML shorthand and the HTML output. 

**Paragraphs (p) with Modifiers** 

The application supports text modifiers like bold, italics, strong, etc. These are applied using dot notation after the element. 

**Input:** 
```
LightML
p.bold(id='myId') = 'This is bold text with an ID.'
```

**Output:** 
```
HTML
<p id="myId"><b>This is bold text with an ID.</b></p>
```
Modifiers include: 
- bold: Wraps the text in <b>. 
- italics: Wraps the text in <i>. 
- strong: Wraps the text in <strong>. 
- Other supported tags: mark, small, del, ins, sub, sup. 

Text can also be included by writing “text = ‘user-content’” 

**Input:** 
```
LightML
text = 'This is text.'
```

**Output:** 
```
HTML
<p>This is text.</p>
```
<hr>
**Divs (div)**

The shorthand for creating a div block supports attributes and nested content inside {}. You can include classes and IDs inside the div tag. Div’s also support multiple nested elements. 

**Input:** 
```
LightML
div(class='container') {
p = 'This is inside a container div.' }
```

**Output:** 
```
HTML
<div class="container">
    <p>This is inside a container div.</p> 
</div>
```
<hr>
**Forms (form) and Input Fields**

Forms are declared using the form keyword, and input fields are declared using their respective types (e.g., input-text, input-email). 

**Input:** 
```
LightML
form {
 input-text(id='username') = 'username'
 input-email(id='email') = 'email'
}
```
**Output:** 
```
HTML
<form>
 <input type="text" id="username" name="username">
 <input type="email" id="email" name="email">
</form>
```
<hr>
**Links** 

Shorthand for creating hyperlinks follows the format: (link = 'linkText,URL'). **Input:** 

```
LightML
link = 'Google, https://www.google.com'
```
**Output:** 
```
HTML
<a href="https://www.google.com">Google</a>
```
<hr>
**Images (img)** 

Shorthand for images allows attributes such as alt, width, height, etc. Attributes are defined inside parentheses after img. 

**Input:** 
```
LightML
img(alt='A sample image' width='300') = 'image.jpg' 
```
You can also use image instead of img in LightML. 
```
LightML
image(alt='A sample image' width='300') = 'image.jpg' 
```
**Output:** 
```
HTML
<img src="image.jpg" alt="A sample image" width="300">
```
<hr>
**Scripts and Stylesheets** 
Scripts and stylesheets can be added using the script/js and css/stylesheet keywords. The path to the file is given after =. 

**Input:** 
```
LightML
css = 'styles.css' script = 'app.js'

LightML (alternate)
stylesheet = 'styles.css' js = 'app.js'
```
**Output:** 
```
HTML
<link rel="stylesheet" href="styles.css"> <script src="app.js" defer></script> 
```
<hr>
**Layout Frameworks: Bootstrap and Tailwind** 

The application supports layout frameworks like **Bootstrap** and **Tailwind** with simple shorthand commands. 

```
LightML
bootstrap tailwind 
```
**Output for Bootstrap:** 
```
HTML
<p><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous"> </p><p><script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script> </p>
```

**Output for Tailwind:** 
```
HTML
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
```

**Labels** 
Labels can be defined with a for attribute linked to an input field. **Input:** 

```
LightML
label = 'username' 
```
**Output:** 
```
HTML
<label for="username">username</label>
```
<hr>
**Line Breaks (br) and Horizontal Rules (hr)** 

**Input:** 
```
LightML
br
hr
```
**Output:** 
```
HTML
<br> 
<hr>
```

**Raw HTML** 

In order to build the shorthand as flexible as possible I added the ability to directly inject raw HTML into the shorthand. The html content is stored inside parentheses html(raw-html) 

**Input:** 
```
LightML
<p>html(<div> </p><p>` `<p>this text</p> </div>) </p>
```

**Output:** 
```
HTML
<p><div> </p><p>` `<p>this text</p> </div> </p>
```

With this you can also add html directly to the head of the document if needed:
```
LightML
html-head(<div>  <p>this text</p> </div>)
```
**Output:**
```
HTML
<head>
 <div>
 <p>this text</p>
 </div>
</head>

```
<hr>
**Example Shorthand File** 

Here’s an example of a LightML file and the HTML that it translates into: 

**LightML input:**
```
LightML
title = 'My Web Page'
css = 'styles.css'
nav {
 Home = 'index.html'
 About = 'about.html'
}
div(class='container') {
 p.bold(id='intro') = 'Welcome to my website!'
 p = 'This is a simple paragraph.'
 img(alt='Sample Image', width='400') = 'sample.jpg'
 br
 p.strong = 'This text is strong and important.'
}
form {
 label = 'username'
 input-text(id='username') = 'username'
 input-email(id='email') = 'email'
 br
 input-text(id='password') = 'password'
}
footer {
 p = 'This is the footer content.'
}
```
**HTML Output** 
```
HTML
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <meta http-equiv="X-UA-Compatible" content="ie=edge">
 <title>My Web Page</title>
 <link rel="stylesheet" href="styles.css">
</head>
<body>
 <nav>
 <a href="index.html">Home</a>
 <a href="about.html">About</a>
 </nav>
 <div class="container">
 <p id="intro"><b>Welcome to my website!</b></p>
 <p>This is a simple paragraph.</p>
 <img src="sample.jpg" alt="Sample Image" width="400">
 <br>
 <p><strong>This text is strong and important.</strong></p>
 </div>
 <form>
 <label for="username">username</label>
 <input type="text" id="username" name="username">
 <input type="email" id="email" name="email">
 <br>
 <input type="text" id="password" name="password">
 </form>
 <footer>
 <p>This is the footer content.</p>
 </footer>
</body>
</html>
```
<hr>
**Future Expansion** 

As the application evolves, I aim to expand its functionality by adding support for additional HTML tags and incorporating more advanced shorthand for popular CSS frameworks like **Bootstrap** and **Tailwind**. This section outlines some of the future features and shorthand syntax that will be added. 

**Framework-Specific Shorthand: Bootstrap and Tailwind** 

The application will include predefined shorthand commands for generating common **Bootstrap** and **Tailwind** components, making it easier to build responsive layouts with minimal coding effort. 

**Bootstrap Shorthand Syntax** 

Examples of the planned Bootstrap-specific shorthand commands: 

**bs-gallery-3x3**: A Bootstrap grid-based gallery layout with x rows and x columns. The shorthand will generate a responsive gallery using the Bootstrap grid system. 

**Input:** 
```
LightML
bs-gallery-3x3 = './imagesfolder/' 
```
**Output:** 
```
HTML
<div class="row">
 <div class="col-4">
 <img src="./imagesfolder/image1.jpg" class="img-fluid">
 </div>
 <div class="col-4">
 <img src="./imagesfolder/image2.jpg" class="img-fluid">
 </div>
 <div class="col-4">
 <img src="./imagesfolder/image3.jpg" class="img-fluid">
 </div>
 <!-- More columns for the 3x3 grid -->
</div>
```
The LightML shorthand will allow you to declare folders of images to be used in a gallery. 

**bs-navigation:** A shorthand for generating a Bootstrap navbar with customizable links 

and branding. **Input:** 
```
LightML
bs-navigation{
brand='MySite', logo =’MyLogo.jpg’, 
links='Home:index.html,About:about.html,Contact:contact.html'
}
```
**Output:** 
```
HTML
<nav class="navbar navbar-expand-lg navbar-light bg-light">
 <a class="navbar-brand" href="#">MySite</a>
 <div class="collapse navbar-collapse">
 <ul class="navbar-nav">
 <li class="nav-item">
 <a class="nav-link" href="index.html">Home</a>
 </li>
 <li class="nav-item">
 <a class="nav-link" href="about.html">About</a>
 </li>
 <li class="nav-item">
 <a class="nav-link" href="contact.html">Contact</a>
 </li>
 </ul>
 </div>
</nav>
```

**Tailwind Shorthand Syntax** 

For developers working with **Tailwind CSS**, the application will include similar shorthand commands to quickly generate Tailwind components with utility classes. 

**tw-gallery-3x3:** Similar to the previous plan for bootstrap. A 3x3 image gallery using Tailwind's utility classes for responsive layout. More sizes could also be added 

**Input:** 
```
LightML
tw-gallery-3x3 = './imagesfolder/'
```
**Output:** 
```
HTML
<div class="grid grid-cols-3 gap-4">
 <img src="./imagesfolder/image1.jpg" class="w-full h-auto">
 <img src="./imagesfolder/image2.jpg" class="w-full h-auto">
 <img src="./imagesfolder/image3.jpg" class="w-full h-auto">
</div>
```
<hr>
**Expanding HTML Element Support** 

In addition to the framework-specific components, the application will support an extended range of common HTML elements and components to streamline development. 

**New Element Support (Planned):** 

- **Lists (ul, ol, li)**: Add shorthand for generating ordered and unordered lists with nested list items. 
- **Tables (table, tr, td):** Shorthand for creating tables with rows and columns. 
- **Media (Audio/Video):** Plan to support multimedia elements like audio and video. 

**Conclusion** 

This application offers a flexible shorthand for generating HTML documents. It supports a wide range of HTML elements and allows customization through modifiers and attributes. The ability to include layout frameworks like Bootstrap and Tailwind makes it even more powerful for rapid HTML development. In future versions, the application will be significantly enhanced to support more HTML tags and a richer shorthand syntax for frameworks like Bootstrap and Tailwind. These additions will streamline the creation of responsive layouts and complex components, further simplifying the web development process. 

Let me know if you'd like further clarification or more examples! 
