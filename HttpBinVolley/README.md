HttpBinVolley
===

This example can do GET and POST requests using [httpbin](http://httpbin.org) website, [Volley](https://android.googlesource.com/platform/frameworks/volley/) library
and [Otto](http://square.github.io/otto/) event bus. You are able to provide URL arguments, attach JSON to the request body and read the server response.

Available for API 8+.

Note
---

There are some problems with focus when trying to add args or JSON parameters. Seems like [others](http://stackoverflow.com/questions/3468765/buggy-listview-makes-me-sad)
have stumbled upon such problems too:

> It sounds like ListViews aren't able to handle EditTexts well. I've done some research and the consensus seems to be "don't do that."

I'll try to make it work, please bear with me until then.
