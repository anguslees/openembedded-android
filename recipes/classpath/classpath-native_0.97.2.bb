require classpath-native.inc

PR = "r7"

# The code affected by the javanet-local patch
# is usually not compiled. However if someone changes
# to --enable-local-sockets it will.
SRC_URI += "\
  file://netif_16.patch;striplevel=0 \
  file://SimpleName.diff;striplevel=0 \
  file://javanet-local.patch;striplevel=0 \
  file://sun-security-getproperty_0.96.1.patch;striplevel=0 \
  file://ecj_java_dir.patch \
  file://autotools.patch \
  file://decimalformat.patch \
  file://cp-byte-loophelper.patch;striplevel=0 \
  file://miscompilation.patch \
  file://toolwrapper-exithook.patch \
  "

do_unpackpost() {
  # Kind of patch: Moves package "org.w3c.dom.html2" to "org.w3c.dom.html"
	mv external/w3c_dom/org/w3c/dom/html2 \
			external/w3c_dom/org/w3c/dom/html

	find examples/gnu/classpath/examples/html gnu/xml/dom/html2 external/w3c_dom/org/w3c/dom/html -name "*.java" \
		-exec sed -i -e"s|org.w3c.dom.html2|org.w3c.dom.html|" {} \;

	sed -i -e"s|org/w3c/dom/html2|org/w3c/dom/html|" external/w3c_dom/Makefile.am
}

addtask unpackpost after do_unpack before do_patch


SRC_URI[md5sum] = "6a35347901ace03c31cc49751b338f31"
SRC_URI[sha256sum] = "001fee5ad3ddd043783d59593153510f09064b0d9b5aea82f535266f62f02db4"
