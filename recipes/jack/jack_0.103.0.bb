DESCRIPTION = "JACK is a low-latency audio server. It can \
connect a number of different applications to an audio \
device, as well as allowing them to share audio between \
themselves."
SECTION = "libs/multimedia"
PRIORITY = "optional"
LICENSE = "GPL LGPL"
PR = "r0"

DEPENDS = "alsa-lib"

SRC_URI = "${SOURCEFORGE_MIRROR}/jackit/jack-audio-connection-kit-${PV}.tar.gz"
S = "${WORKDIR}/jack-audio-connection-kit-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF = "--enable-timestamps --disable-capabilities --disable-oldtrans \
		--disable-portaudio --disable-coreaudio --enable-oss --enable-alsa"

EXTRA_OEMAKE = 'transform="s,^,,"'
LDFLAGS_append = " -ldl -L${STAGING_LIBDIR}"

PACKAGES =+ "libjack jack-server jack-examples"

FILES_libjack = "${libdir}/*.so.* ${libdir}/jack/*.so"
FILES_jack-server = "${bindir}/jackd"
FILES_jack-examples = "${bindir}/*"

do_stage() {
       autotools_stage_all
}


SRC_URI[md5sum] = "03a0f63b997ce7b83a1eeaa6b80f4388"
SRC_URI[sha256sum] = "a5d9fd696d7ee4a1c7679b5a688155bc1e0abbdf5f144d6762dbbee874df235f"
