DESCRIPTION = "ARToolKit is a software library that allows for the creation of augmented reality applications. "
LICENSE = "GPL/ARToolkit"

DEPENDS = "libxi gstreamer virtual/libx11 freeglut virtual/libgl"

PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/artoolkit/ARToolKit-${PV}.tgz \
           file://configure.diff \
           file://paddle.diff "

S = "${WORKDIR}/ARToolKit"

LDFLAGS_append = " `pkg-config --libs gstreamer-0.10` -lpthread -lglut -lGLU -lGL -lXi -lX11 -lm"

do_configure() {
	for i in $(find ${S} -name Makefile.in) ; do
		sed -i -e /^CC/d \ 
		       -e s/cc\ /'${CXX}'\ /g \
		       -e s/ar\ /${AR}\ /g \
		       -e 's:$(LDFLAG):$(LDFLAG) $(LDFLAGS):g' \
		       -e 's:$(CFLAG):$(CFLAG) $(CFLAGS):g'\
		       $i
	done
	./Configure << EOF
5
y
n
EOF
}

export CC

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/bin/[a-z]* ${D}/${bindir}

	install -d ${D}/${datadir}/ARToolKit
	cp -pPr ${S}/bin/{Data,Wrl} ${D}/${datadir}/ARToolKit/

	install -d ${D}${libdir}
	install -m 0644 ${S}/lib/*.a ${D}${libdir}
}

FILES_${PN} += "${datadir}/ARToolKit"





SRC_URI[md5sum] = "4328b512711529309bad4bd1d791952c"
SRC_URI[sha256sum] = "1a49a67c6e196b0e9990e90255ce840287ac47b5a53371a68a6dd93a5425d259"
