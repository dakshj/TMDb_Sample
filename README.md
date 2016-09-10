#The Movie Database ([TMDb](https://www.themoviedb.org/)) Sample Android App [![Build Status](https://travis-ci.org/dakshj/TMDb_Sample.svg?branch=master)](https://travis-ci.org/dakshj/TMDb_Sample)

##Architectural Pattern
The Model-View-Presenter architectural pattern has been used to build this app.
[\[1\]](https://github.com/googlesamples/android-architecture/tree/todo-mvp/)
[\[2.a\]](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
[\[2.b\]](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)
[\[3\]](http://engineering.remind.com/android-code-that-scales/)

##Dependency Injection
Dagger 2 is used as a DI library.
[\[1\]](https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2)
[\[2\]](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)

It has been used both in
[`main`](app/src/main/java/com/daksh/tmdbsample/di)
as well as in
[`test`](app/src/test/java/com/daksh/tmdbsample/di).

Each `View` has its own `@Module` and `@Subcomponent`.

A `Presenter` is then `@Inject`ed into the implementation of the `View` (i.e., `Activity`, `Fragment`, etc.).
[\[1\]](http://www.technicaladvices.com/2016/04/07/dagger-2-mvp-and-unit-testing-android-di-part-3/)

##Networking & Reactive Execution
[Retrofit](http://square.github.io/retrofit/)
has been used in conjuction with
[RxJava](https://github.com/ReactiveX/RxJava)
[`Single`s](https://medium.com/@kurtisnusbaum/rxandroid-basics-part-1-c0d5edcf6850#.bhaiac8pk)
to fetch data from an API.

This JSON data is then parsed using [Gson](https://github.com/google/gson).

Gson is also used via DI to parse sample responses into
[`MovieListApiResponse`](app/src/main/java/com/daksh/tmdbsample/data/model/MovieListApiResponse.java)
in Unit Tests.

##Testing
[Robolectric](http://robolectric.org/)
and
[Mockito](http://mockito.org/)
are used for Unit Tests on the JVM, and
[Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html)
is used for Instrumentation Tests.
[\[1\]](https://guides.codepath.com/android/Unit-Testing-with-Robolectric)
[\[2\]](https://github.com/googlesamples/android-architecture/tree/todo-mvp/todoapp/app/src/test/java/com/example/android/architecture/blueprints/todoapp)

##Continuous Integration
[Travis CI](https://travis-ci.org/)
has been used for adding CI to the project.
[\[1\]](https://guides.codepath.com/android/Setting-up-Travis-CI)
[\[2\]](https://guides.codepath.com/android/Setting-up-Travis-CI#troubleshooting)


##License

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.