DESCRIPTION = "Pine(R) - a Program for Internet News & Email"
SECTION = "console/network"
LICENSE = "Pine"
DEPENDS = "ncurses openssl"
RDEPENDS_pine = "pico"
HOMEPAGE = "http://www.washington.edu/pine/"

SRC_URI = "ftp://ftp.cac.washington.edu/pine/pine${PV}.tar.Z"
S = "${WORKDIR}/pine${PV}"

inherit autotools

#
# ~lart Pine's build structure...
#
PARALLEL_MAKE = ""
EXTRA_OEMAKE = "-e"
export MAKE = 'MAKE="make -e" make -e'

CFLAGS += "-I${S}/imap/c-client"
LDFLAGS += "${S}/imap/c-client/c-client.a -lssl -lcrypt"

export SSLDEFINES = "-DSSL_CERT_DIRECTORY=\\"/etc/ssl/certs\\" -DSSL_KEY_DIRECTORY=\\"/etc/ssl/private\\""
export SSLCFLAGS = "${CFLAGS} -I${STAGING_INCDIR}/openssl ${SSLDEFINES}"
export SSLLDFLAGS = "-lssl -lcrypt ${LDFLAGS} -L${STAGING_LIBDIR}/openssl"
export EXTRALDFLAGS = "${LDFLAGS}"

do_configure() {
       ln -sf ${S}/imap/c-client ${S}/c-client
       cd ${S}/pico/osdep && \
       ${BUILD_CC} -o includer includer.c
       cd ${S}/pine/osdep && \
       ${BUILD_CC} -o includer includer.c
}

do_compile() {
       unset CFLAGS && unset LDFLAGS
       cd ${S}/imap && oe_runmake slx

       for i in pico pine
       do
               cd ${S}/$i && oe_runmake -f makefile.lnx
       done
}

BINARIES = "imap/mailutil/mailutil imap/mlock/mlock pico/pico pico/pilot pine/pine"

do_install() {
       install -d 0644 ${D}${bindir}
       for binary in ${BINARIES}
       do
               install -m 0755 $binary ${D}${bindir}
       done
}

PACKAGES = "${PN}-dbg pico pine"
FILES_pico = "${bindir}/pico ${bindir}/pilot"


SRC_URI[md5sum] = "75af127948cc0c701d424d22d621f792"
SRC_URI[sha256sum] = "62c7b97695cb486e420afbbbe21bef853dd68a71665d867ffef50cb34dfba5f4"
