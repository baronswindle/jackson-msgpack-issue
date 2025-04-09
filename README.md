To test, run `mvn test`

There seem to be two related changes that are interacting to cause this issue. One change was in the msgpack-java-dataformat library and seems to have been included in the v0.8.17 release. The other change was in the jackson-jakarta-rs-base library and was introduced in v2.15.
* [msgpack change](https://github.com/msgpack/msgpack-java/commit/85e59ee48153229b336ba519ae561091d38602f5) and [related GitHub issue](https://github.com/msgpack/msgpack-java/issues/498)
* [Jackson change](https://github.com/FasterXML/jackson-jakarta-rs-providers/commit/3649040d568f2594d00fbda1efbbf704f0e639d3) and [related GitHub issue](https://github.com/FasterXML/jackson-jaxrs-providers/issues/108)

I've been able to resolve the issue in three ways, but none of them seem ideal:
* Downgrade Java MessagePack libraries to <= v0.8.16
* Downgrade Jackson libraries to <= 2.14.x
* Disable `JakartaRSFeature.READ_FULL_STREAM` on the JakartaRS Jackson Provider

