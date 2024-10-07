**Light ML Documentation** 

**A shorthand markup language for HTML**  

**By Paul Craig  07/10/2024**  

**Overview** 

This is a Java application that converts a custom shorthand text format into well- formatted HTML. It supports various HTML elements like paragraphs, divs, forms, links, images, and even advanced layout frameworks like Bootstrap and Tailwind. Each element can include attributes (e.g., id, class) and modifiers for text styling (e.g., bold, italics). 

**Key Features** 

- Supports most common HTML elements like paragraphs (p), divs (div), links (a), forms (form), and images (img). I will expand this in the future. 
- Allows the use of text modifiers such as bold, italics, and strong. 
- Supports shorthand input for adding CSS links, JavaScript files, and raw HTML. 
- Handles layout frameworks like Bootstrap and Tailwind with simple shorthand commands. 
- Supports attributes for HTML tags (e.g., id, class), and these can be included inside parentheses. 

**Shorthand Syntax** 



|**General syntax format** |
| - |
|Element.modifiers(attributes) = 'content' |



- **element**: The HTML tag you want to generate (e.g., p, div, img, a, etc.). 
- **modifiers**: Optional text modifiers like bold, italics, strong (for paragraphs). 
- **attributes**: Optional attributes enclosed in parentheses (e.g., id='example', class='myClass'). 
- **content**: The inner content to be placed inside the element, enclosed in single quotes ('). For some tags the content will sit inside the tags <p>content</p> for others it appends inside the tag <img src=content>. 

**Supported Elements** 

Here’s a breakdown of the supported elements with their LightML shorthand and the HTML output. 

**Paragraphs (p) with Modifiers** 

The application supports text modifiers like bold, italics, strong, etc. These are applied using dot notation after the element. 

**Input:** 



|**LightML** |
| - |
|p.bold(id='myId') = 'This is bold text with an ID.' |

**Output:** 

|**HTML** |
| - |
|<p id="myId"><b>This is bold text with an ID.</b></p> |

Modifiers include: 

- bold: Wraps the text in <b>. 
- italics: Wraps the text in <i>. 
- strong: Wraps the text in <strong>. 
- Other supported tags: mark, small, del, ins, sub, sup. 

Text can also be included by writing “text = ‘user-content’” **Input:** 



|**LightML** |
| - |
|text = 'This is text.' |

**Output:** 

|**HTML** |
| - |
|<p>This is text.</p> |

**Divs (div)**

The shorthand for creating a div block supports attributes and nested content inside {}. You can include classes and IDs inside the div tag. Div’s also support multiple nested elements. 

**Input:** 



|LightML |
| - |
|<p>div(class='container') { </p><p>`    `p = 'This is inside a container div.' } </p>|

**Output:** 



|**HTML** |
| - |
|<p><div class="container"> </p><p>`    `<p>This is inside a container div.</p> </div> </p>|

**Forms (form) and Input Fields**

Forms are declared using the form keyword, and input fields are declared using their respective types (e.g., input-text, input-email). 

Input: 



|LightML |
| - |
|<p>form { </p><p>`    `input-text(id='username') = 'username'     input-email(id='email') = 'email' </p><p>} </p>|

**Output:** 



|**HTML** |
| - |
|<p><form> </p><p>`    `<input type="text" id="username" name="username">     <input type="email" id="email" name="email"> </form> </p>|

**Links** 

Shorthand for creating hyperlinks follows the format: (link = 'linkText,URL'). **Input:** 



|**LightML** |
| - |
|link = 'Google,https://www.google.com' |

**Output:** 



|**HTML** |
| - |
|<a href="https://www.google.com">Google</a> |

**Images (img)** 

Shorthand for images allows attributes such as alt, width, height, etc. Attributes are defined inside parentheses after img. 

**Input:** 



|LightML |
| - |
|img(alt='A sample image' width='300') = 'image.jpg' |

You can also use image instead of img in LightML. 



|LightML |
| - |
|image(alt='A sample image' width='300') = 'image.jpg' |

**Output:** 



|**HTML** |
| - |
|<img src="image.jpg" alt="A sample image" width="300"> |

**Scripts and Stylesheets** 

Scripts and stylesheets can be added using the script/js and css/stylesheet keywords. The path to the file is given after =. 

**Input:** 



|**LightML** |
| - |
|css = 'styles.css' script = 'app.js' |
|**LightML (alternate)** |
|stylesheet = 'styles.css' js = 'app.js' |

**Output:** 



|**HTML** |
| - |
|<link rel="stylesheet" href="styles.css"> <script src="app.js" defer></script> |

**Layout Frameworks: Bootstrap and Tailwind** 

The application supports layout frameworks like **Bootstrap** and **Tailwind** with simple shorthand commands. 



|**LightML** |
| - |
|bootstrap tailwind |

**Output for Bootstrap:** 



|**HTML** |
| - |
|<p><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous"> </p><p><script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script> </p>|

**Output for Tailwind:** 



|**HTML** |
| - |
|<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet"> |

**Labels** 

Labels can be defined with a for attribute linked to an input field. **Input:** 



|**LightML** |
| - |
|label = 'username' |

**Output:** 



|**HTML** |
| - |
|<label for="username">username</label> |

**Line Breaks (br) and Horizontal Rules (hr)** 

The application supports the shorthand for adding line breaks (<br>) and horizontal rules (<hr>). 

**Input:** 



|**LightML** |
| - |
|br hr |

**Output:** 



|**HTML** |
| - |
|<br> <hr> |

**Raw HTML** 

In order to build the shorthand as flexible as possible I added the ability to directly inject raw HTML into the shorthand. The html content is stored inside parentheses html(raw-html) 

**Input:** 



|**LightML** |
| - |
|<p>html(<div> </p><p>` `<p>this text</p> </div>) </p>|

**Output:** 



|**HTML** |
| - |
|<p><div> </p><p>` `<p>this text</p> </div> </p>|

With this you can also add html directly to the head of the document if needed: 



|**LightML** |
| - |
|html-head(<div>  <p>this text</p> </div>) |



|**HTML** |
| - |
|<p><head> </p><p>`    `<div> </p><p>`        `<p>this text</p>     </div> </p><p></head> </p>|

**Example Shorthand File** 

Here’s an example of a LightML file and the HTML that it translates into: 

**LightML input:**



|**LightML** |
| - |
|<p>title = 'My Web Page' css = 'styles.css' </p><p>nav { </p><p>`    `Home = 'index.html'     About = 'about.html' } </p><p>div(class='container') { </p><p>`    `p.bold(id='intro') = 'Welcome to my website!' </p><p>`    `p = 'This is a simple paragraph.' </p><p>`    `img(alt='Sample Image', width='400') = 'sample.jpg'     br </p><p>`    `p.strong = 'This text is strong and important.' </p><p>} </p><p>form { </p><p>`    `label = 'username' </p><p>`    `input-text(id='username') = 'username'     input-email(id='email') = 'email' </p><p>`    `br </p><p>`    `input-text(id='password') = 'password' } </p><p>footer { </p><p>`    `p = 'This is the footer content.' } </p>|

**HTML Output** 



|**HTML** |
| - |
|<p><!DOCTYPE html> </p><p><html> </p><p><head> </p><p>`    `<meta charset="UTF-8"> </p><p>`    `<meta name="viewport" content="width=device-width, initial-scale=1.0">     <meta http-equiv="X-UA-Compatible" content="ie=edge"> </p><p>`    `<title>My Web Page</title> </p><p>`    `<link rel="stylesheet" href="styles.css"> </p><p></head> </p><p><body> </p><p>`    `<nav> </p><p>`        `<a href="index.html">Home</a> </p><p>`        `<a href="about.html">About</a> </p><p>`    `</nav> </p><p>`    `<div class="container"> </p><p>`        `<p id="intro"><b>Welcome to my website!</b></p> </p><p>`        `<p>This is a simple paragraph.</p> </p><p>`        `<img src="sample.jpg" alt="Sample Image" width="400"> </p><p>`        `<br> </p><p>`        `<p><strong>This text is strong and important.</strong></p> </p><p>`    `</div> </p><p>`    `<form> </p><p>`        `<label for="username">username</label> </p><p>`        `<input type="text" id="username" name="username"> </p><p>`        `<input type="email" id="email" name="email"> </p><p>`        `<br> </p><p>`        `<input type="text" id="password" name="password"> </p><p>`    `</form> </p><p>`    `<footer> </p><p>`        `<p>This is the footer content.</p> </p><p>`    `</footer> </p><p></body> </p><p></html> </p>|

**Future Expansion** 

As the application evolves, I aim to expand its functionality by adding support for additional HTML tags and incorporating more advanced shorthand for popular CSS frameworks like **Bootstrap** and **Tailwind**. This section outlines some of the future features and shorthand syntax that will be added. 

**Framework-Specific Shorthand: Bootstrap and Tailwind** 

The application will include predefined shorthand commands for generating common **Bootstrap** and **Tailwind** components, making it easier to build responsive layouts with minimal coding effort. 

**Bootstrap Shorthand Syntax** 

Examples of the planned Bootstrap-specific shorthand commands: 

**bs-gallery-3x3**: A Bootstrap grid-based gallery layout with x rows and x columns. The shorthand will generate a responsive gallery using the Bootstrap grid system. 

**Input:** 



|**LightML** |
| - |
|bs-gallery-3x3 = './imagesfolder/' |

**Output:** 



|**HTML** |
| - |
|<p><div class="row"> </p><p>`    `<div class="col-4"> </p><p>`        `<img src="./imagesfolder/image1.jpg" class="img-fluid">     </div> </p><p>`    `<div class="col-4"> </p><p>`        `<img src="./imagesfolder/image2.jpg" class="img-fluid">     </div> </p><p>`    `<div class="col-4"> </p><p>`        `<img src="./imagesfolder/image3.jpg" class="img-fluid">     </div> </p><p>`    `<!-- More columns for the 3x3 grid --> </p><p></div> </p>|

The LightML shorthand allows you to declare folders of images to be used in a gallery. 

**bs-navigation:** A shorthand for generating a Bootstrap navbar with customizable links 

and branding. **Input:** 



|**LightML** |
| - |
|<p>bs-navigation{ </p><p>brand='MySite', logo =’MyLogo.jpg’, links='Home:index.html,About:about.html,Contact:contact.html' } </p>|

**Output:** 



|**HTML** |
| - |
|<p><nav class="navbar navbar-expand-lg navbar-light bg-light">     <a class="navbar-brand" href="#">MySite</a> </p><p>`    `<div class="collapse navbar-collapse"> </p><p>`        `<ul class="navbar-nav"> </p><p>`            `<li class="nav-item"> </p><p>`                `<a class="nav-link" href="index.html">Home</a> </p><p>`            `</li> </p><p>`            `<li class="nav-item"> </p><p>`                `<a class="nav-link" href="about.html">About</a> </p><p>`            `</li> </p><p>`            `<li class="nav-item"> </p><p>`                `<a class="nav-link" href="contact.html">Contact</a>             </li> </p><p>`        `</ul> </p><p>`    `</div> </p><p></nav> </p>|

**Tailwind Shorthand Syntax** 

For developers working with **Tailwind CSS**, the application will include similar shorthand commands to quickly generate Tailwind components with utility classes. 

**tw-gallery-3x3:** Similar to the previous plan for bootstrap. A 3x3 image gallery using Tailwind's utility classes for responsive layout. More sizes could also be added 

**Input:** 



|**LightML** |
| - |
|tw-gallery-3x3 = './imagesfolder/' |

**Output:** 



|**HTML** |
| - |
|<p><div class="grid grid-cols-3 gap-4"> </p><p>`    `<img src="./imagesfolder/image1.jpg" class="w-full h-auto">     <img src="./imagesfolder/image2.jpg" class="w-full h-auto">     <img src="./imagesfolder/image3.jpg" class="w-full h-auto"> </div> </p>|

**tw-navbar:** A Tailwind navbar shorthand for generating a responsive navigation bar with a branding logo and links. 

**Input:** 



|**LightML** |
| - |
|tw-navbar{brand='MySite', links='Home:index.html,About:about.html,Contact:contact.html'} |

**Output:** 



|**HTML** |
| - |
|<p><nav class="bg-gray-800 p-4"> </p><p>`    `<div class="container mx-auto"> </p><p>`        `<a href="#" class="text-white text-lg font-bold">MySite</a> </p><p>`        `<div class="flex space-x-4"> </p><p>`            `<a href="index.html" class="text-gray-300">Home</a> </p><p>`            `<a href="about.html" class="text-gray-300">About</a> </p><p>`            `<a href="contact.html" class="text-gray-300">Contact</a>         </div> </p><p>`    `</div> </p><p></nav> </p>|

**Expanding HTML Element Support** 

In addition to the framework-specific components, the application will support an extended range of common HTML elements and components to streamline development. 

**New Element Support (Planned):** 

- **Lists (ul, ol, li)**: Add shorthand for generating ordered and unordered lists with nested list items. 

**Input:** 



|**LightML** |
| - |
|ul = 'Item 1,Item 2,Item 3' |

**Output**: 



|**HTML** |
| - |
|<p><ul> </p><p>`    `<li>Item 1</li>     <li>Item 2</li>     <li>Item 3</li> </ul> </p>|

**Tables (table, tr, td):** Shorthand for creating tables with rows and columns. 

**Input:** 



|**LightML** |
| - |
|table = 'Row1Col1,Row1Col2,Row2Col1,Row2Col2’ |

**Output**: 



|**HTML** |
| - |
|<p><table> </p><p>`    `<tr> </p><p>`        `<td>Row1Col1</td>         <td>Row1Col2</td>     </tr> </p><p>`    `<tr> </p><p>`        `<td>Row2Col1</td>         <td>Row2Col2</td>     </tr> </p><p></table> </p>|

**Media (Audio/Video):** Plan to support multimedia elements like audio and video. 

**Input:** 



|**LightML** |
| - |
|Video(src='video.mp4', controls='true') |

**Output**: 



|**HTML** |
| - |
|<p><video src="video.mp4" controls> </p><p>`    `Your browser does not support the video tag. </video> </p>|

**Conclusion** 

This application offers a flexible shorthand for generating HTML documents. It supports a wide range of HTML elements and allows customization through modifiers and attributes. The ability to include layout frameworks like Bootstrap and Tailwind makes it even more powerful for rapid HTML development. In future versions, the application will be significantly enhanced to support more HTML tags and a richer shorthand syntax for frameworks like Bootstrap and Tailwind. These additions will streamline the creation of responsive layouts and complex components, further simplifying the web development process. 

Let me know if you'd like further clarification or more examples! 
