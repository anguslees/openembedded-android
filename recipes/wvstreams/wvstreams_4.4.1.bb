HOMEPAGE = "http://alumnit.ca/wiki/index.php?page=WvStreams"
LICENSE = "LGPL"
DESCRIPTION = "WvStreams is a network programming library in C++"
DEPENDS = "zlib openssl (>= 0.9.8)"

PR = "r3"

SRC_URI = "http://wvstreams.googlecode.com/files/${PN}-${PV}.tar.gz \
	file://build-fixes-and-sanity.patch \
	file://dont-forget-header.patch \
	file://gcc4.3.patch \
	"


inherit autotools pkgconfig

LDFLAGS_append = " -Wl,-rpath-link,${CROSS_DIR}/${TARGET_SYS}/lib"

EXTRA_AUTORECONF += " -I${S}/gnulib/m4"
EXTRA_OECONF = " --without-tcl --without-qt --without-pam"

PACKAGES_prepend = "libuniconf libuniconf-dbg "
PACKAGES_prepend = "uniconfd uniconfd-dbg "
PACKAGES_prepend = "libwvstreams-base libwvstreams-base-dbg "
PACKAGES_prepend = "libwvstreams-extras libwvstreams-extras-dbg "

FILES_libuniconf     = "${libdir}/libuniconf.so.*"
FILES_libuniconf-dbg = "${libdir}/.debug/libuniconf.so.*"

FILES_uniconfd     = "${sbindir}/uniconfd ${sysconfdir}/uniconf.conf ${localstatedir}/uniconf"
FILES_uniconfd-dbg = "${sbindir}/.debug/uniconfd"

FILES_libwvstreams-base     = "${libdir}/libwvutils.so.*"
FILES_libwvstreams-base-dbg = "${libdir}/.debug/libwvutils.so.*"

FILES_libwvstreams-extras     = "${libdir}/libwvbase.so.* ${libdir}/libwvstreams.so.*"
FILES_libwvstreams-extras-dbg = "${libdir}/.debug/libwvbase.so.* ${libdir}/.debug/libwvstreams.so.*"


do_stage() {
    autotools_stage_all
}

SRC_URI[md5sum] = "907b0cba8931553d848620ea16f006b9"
SRC_URI[sha256sum] = "cff48f9a87e94672c7a819661a3a5c893ee44e763c6e5775d00aff3de43619b1"
