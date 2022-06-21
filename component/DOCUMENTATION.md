Here it is demonstrated how to document a component class in an understandable manner.

## Class level documentation
 
Class level documentation provides an overview of a component.
 
The description should be concise and understandable to reader.
 
Better grammatical sentence structure is appreciated.

Because the documentation maybe read by people from different backgrounds.
    
#### Example: 
    
/**
  * **ButtonPrimaryFull** is another button variant. 
  * 
  * This button is used to show special actions to be performed.
  *
  */

## Usage
  
Usage section provides a description on general usage of the component.
 
This section consists of two parts. 
  
 1. XML declaration
 2. Item implementation
    
### XML declaration

This section describes how the component is inserted into a XML layout file. 

If the component has specific xml attributes, it is recommended to show them with an appropriate code snippet.

Another important thing to pay attention is, code snippet should show only the mandatory attributes including component specific attributes.

**It is unnecessary to include margins or padding to the parent layout.**

#### Example 

```markdown
   * ### XML declaration
     *
     * [ButtonPrimaryFull] is declared in a XML file as below.
     *
     * ```xml
     *      <com.myxlultimate.component.token.button.ButtonPrimaryFull
     *          android:id="@+id/buttonFullPrimary"
     *          android:layout_width="match_parent"
     *          android:layout_height="wrap_content"
     *          app:text="Button text"
     *          android:layout_marginBottom="16dp"/>
     * ```
     *
```

### Kotlin implementation

In this section, it is expected to showcase kotlin property initialization and public api implementations if there are any.

Adequate and concise content will make the reader interested in going through the documentation.

A code snippet is highly expected in this section to make the reader understand well.

#### Example

```markdown
 * ### Kotlin implementation
   *
   * ```kotlin
   *      buttonFullPrimary.setOnClickListener {
   *          Toast.makeText(activity, "Text test", Toast.LENGTH_SHORT).show()
   *      }
   * ```
```   
  
To see the full documentation and understand the guidelines well, please visit [here](src/main/java/com/myxlultimate/component/token/button/ButtonPrimaryFull.kt)
