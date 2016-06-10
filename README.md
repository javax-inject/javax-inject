JSR-330: Dependency Injection for Java.
------

[The final specification for the JSR-330 annotations][spec] was released on
October 13th, 2009. You can view the raw materials of the specification
at any time in source control.

You can also view the [javadoc for the spec classes][javadoc] here.


To follow the progress of the expert group, messages from the expert group
mailing list are forwarded to an [observer mailing list] which anyone can
join.

## Implementations

The following dependency injection systems have passed the [TCK][release]:

  * [Google Guice 2.1](http://github.com/google/guice)
  * [KouInject](http://kouinject.googlecode.com/)
  * [OpenWebBeans](http://openwebbeans.apache.org/1.0.0-SNAPSHOT/jsr330.html)
  * [Spring Framework 3.0](http://www.springsource.com/download/community)
  * [Weld 1.0.0](http://www.seamframework.org/Weld)

## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Project Members

| Googlecode User Info           | Project Role | Spec Role | Notes           | 
| ------------------------------ | ------------ | --------- | --------------- |
| `crazybob...@gmail.com`        | Owner        | Lead      | Google          |
| `johnsonr...@gmail.com`        | Owner        | Lead      | SpringSource    |
| `limpbizkit`                   | Committer    | EG Member | Google          |
| `gavin.k...@gmail.com`         | Committer    | EG Member | RedHat          |
| `Larry.Ca...@gmail.com`        | Committer    | EG Member | Oracle          |
| `t...@peierls.net`             | Committer    | EG Member |                 |
| `ja...@maven.org`              | Committer    | EG Member | Maven           |
| `PaulHamm...@gmail.com`        | Committer    | EG Member | PicoContainer   |
| `juergen....@springsource.com` | Committer    | EG Member | SpringSource    |
| `james.st...@gmail.com`        | Committer    | EG Member |                 |
| `roberto....@sun.com`          | Committer    | EG Member | Sun             |
| `hls...@gmail.com`             | Committer    | EG Member | Tapestry        |
| `rickardo...@gmail.com`        | Committer    | EG Member | Qi4j            |
| `d...@cs.oswego.edu`           | Committer    | EG Member |                 |
| `abuck...@gmail.com`           | Committer    | EG Member | Sun             |
| `bokowski`                     | Committer    | EG Member | IBM             |

> Note: The above list was pulled from the google-code site as-is

[spec]: http://javax-inject.github.io/javax-inject/api/javax/inject/package-summary.html
[observer mailing list]: http://groups.google.com/group/atinject-observer
[javadoc]: http://javax-inject.github.io/javax-inject/api/index.html
[release]: https://github.com/javax-inject/javax-inject/releases/tag/1
