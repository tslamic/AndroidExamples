View.INVISIBLE vs. View.GONE
============================

Motivation  
-

If you've done any Android programming, I bet you have stumbled upon Views. They represent the basic building block for any UI component and you are looking at them every time you’re using your (Android) phone. 

Sometimes, you’ll want to make a View visible or hidden. Android allows us to set the visibility by either:

 - calling the `setVisibility` method  
 - specifying the `android:visibility` XML tag  

There are three values/constants we can use as an argument: 

 - `View.VISIBLE`
 - `View.INVISIBLE`
 - `View.GONE`

There’s no need explaining the first one, but what is the difference between the last two? If you’re not sure, read on.

The difference
-

Here's the definition from the docs:

- `View.INVISIBLE` makes the View invisible, but it still **takes up space** for layout purposes.
 

- `View.GONE` makes the View invisible, and it **doesn't take any space** for layout purposes.

Can you spot the difference? Both values set the View to be invisible, but only one of them allocates the needed space in the parent layout. Which one?

Definition Explained
-

So what does it really mean to *take up space for layout purposes*? Imagine a layout that has three TextViews, all visible, like so:
![Main](http://i.imgur.com/DY1U4VK.png).


Suppose we want to make the second TextView invisible. Can you tell which of the following two pics is set to `View.INVISIBLE`? What about `View.GONE`?

![Invisible](http://i.imgur.com/YLGxZc0.png) *PIC A*
![Gone](http://i.imgur.com/eJJFrnR.png) *PIC B*

Almost there...
-

*PIC A* is set to `View.INVISIBLE`. Why? You see that big gap between the TextViews FIRST and THIRD? That's the *space for layout purposes*. In other words, the layout makes space for the TextView even though we cannot see it. 

If you look at the *PIC B*, you'll see no such gap. That is because we've set the visibility to `View.GONE`, which tells the layout to act as if no component is actually there. 

Conclusion
-

Simply put, `View.INVISIBLE` just hides the View but you can still see the void where the hidden View should be. `View.GONE` makes the View completely hidden just like it wouldn't exist.

Go on and try the app if you don't believe me! :-)