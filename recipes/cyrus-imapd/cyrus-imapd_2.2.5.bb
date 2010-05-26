SECTION = "console/network"
DEPENDS = "cyrus-sasl"
PR = "r2"
LICENSE = "BSD"

SRC_URI = "ftp://ftp.andrew.cmu.edu/pub/cyrus-mail/OLD-VERSIONS/imap/cyrus-imapd-${PV}.tar.gz \
           file://autotools.patch \
           file://tail.patch"

inherit autotools

EXTRA_OECONF = "--with-auth=unix \
		--without-perl"

BUILD_CFLAGS += " -I${S} -I${S}/et"
#do_compile_prepend () {
#	cd lib
#	ccache arm-linux-gcc -L/home/kergoth/code/build-arm/tmp/staging/arm-linux/lib -Wl,-rpath-link,/home/kergoth/code/build-arm/tmp/staging/arm-linux/lib -o mkchartable mkchartable.o xmalloc.o assert.o
#	${BUILD_CC} ${BUILD_CFLAGS} mkchartable.c -c -o mkchartable.o
#	${BUILD_CC} ${BUILD_CFLAGS} xmalloc.c -c -o xmalloc.o
#	${BUILD_CC} ${BUILD_CFLAGS} assert.c -c -o assert.o
#	${BUILD_CC} ${BUILD_LDFLAGS} -o mkchartable mkchartable.o xmalloc.o assert.o
#	rm -f xmalloc.o assert.o mkchartable.o
#	cd ..
#}

SRC_URI[md5sum] = "ad8e3ca17b04a38c934f8c7a80c8adec"
SRC_URI[sha256sum] = "8f3b8a3076c16f21ef2912c29033975fb6072ceb68471f15d8a53d833f2873e7"
