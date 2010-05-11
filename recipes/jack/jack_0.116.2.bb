DESCRIPTION = "JACK is a low-latency audio server. It can \
connect a number of different applications to an audio \
device, as well as allowing them to share audio between \
themselves."
SECTION = "libs/multimedia"
PRIORITY = "optional"
LICENSE = "GPL LGPL"
PR = "r1"

DEPENDS = "alsa-lib"

SRC_URI = "http://jackaudio.org/downloads/jack-audio-connection-kit-${PV}.tar.gz"
S = "${WORKDIR}/jack-audio-connection-kit-${PV}"

inherit autotools

EXTRA_OECONF = "--enable-timestamps --disable-capabilities --disable-oldtrans \
		--disable-portaudio --disable-coreaudio --enable-oss --enable-alsa"

EXTRA_OEMAKE = 'transform="s,^,,"'
LDFLAGS_append = " -ldl -L${STAGING_LIBDIR}"

PACKAGES =+ "libjack jack-server jack-examples"

FILES_libjack = "${libdir}/*.so.* ${libdir}/jack/*.so"
FILES_jack-server = "${bindir}/jackd"
FILES_jack-examples = "${bindir}/*"
FILES_${PN}-doc += " ${datadir}/jack-audio-connection-kit/reference/html/* "


SRC_URI[md5sum] = "9c0ae9880e9b6e081f1a238fe6a28bd5"
SRC_URI[sha256sum] = "ce6e1f61a3b003137af56b749e5ed4274584167c0877ea9ef2d83f47b11c8d3d"
