View.INVISIBLE vs. View.GONE
===

Motivation
---

If you've done any Android programming, I bet you have stumbled upon Views. They represent the basic building block for any UI component and you are looking at them every time you’re using your (Android) phone. 

Sometimes, you’ll want to make a View visible or hidden. Android allows us to set the visibility by either:

 - calling the `setVisibility` method  
 - specifying the `android:visibility` XML tag  

There are three values/constants we can use as an argument: 

 - `View.VISIBLE`
 - `View.INVISIBLE`
 - `View.GONE`

Invisible vs. Gone
---

- `View.INVISIBLE` makes the View invisible, but it still **takes up space** for layout purposes.
- `View.GONE` makes the View invisible, and it **doesn't take any space** for layout purposes.

See the difference? Both values set the View to be invisible, but only one of them allocates the needed space in the parent layout.

A picture says a thousand words
---

So what does it really mean to *take up space for layout purposes*? Inspect the pic below:

![image](http://i.imgur.com/U00LgvVl.png)

What is this, I don't even...
---

Let me explain what the above pic represenets (we're observing the elements inside the green box):

- pic A has all three Views set to `View.VISIBLE`
- pic B has the second View set to `View.INVISIBLE`
- pic C has the second View set to `View.GONE`


You see that big gap between the FIRST and THIRD View in pic B? That's the *space for layout purposes*. In other words, the layout still takes up space for the View even though we cannot see it.

If you look at pic C, you'll see no such gap. That is because the layout doesn't take that space. The second Views is simply *gone*.

Conclusion
---

- `View.INVISIBLE` hides the View but you can still see the void where the hidden View should be.
- `View.GONE` makes the View completely hidden just like it wouldn't exist.

Go on and try the app if you don't believe me!