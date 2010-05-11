DESCRIPTION = "An audio Sample Rate Conversion library"
SECTION = "libs"
LICENSE = "GPL libsamplerate"
PR = "r2"

SRC_URI = "http://www.mega-nerd.com/SRC/libsamplerate-${PV}.tar.gz"
S = "${WORKDIR}/libsamplerate-${PV}"

inherit autotools pkgconfig

do_stage() {
	oe_libinstall -a -so -C src libsamplerate ${STAGING_LIBDIR}
	install -m 0644 ${S}/src/samplerate.h ${STAGING_INCDIR}/
}

SRC_URI[md5sum] = "06861c2c6b8e5273c9b80cf736b9fd0e"
SRC_URI[sha256sum] = "98b8766323c78b7b718dfd4ef6b9292bbf0796b742abb2319b8278cbeee731d4"
