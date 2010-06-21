DESCRIPTION = "gxine is a front-end for libxine."
SECTION = "x11/multimedia"
LICENSE = "GPL"
DEPENDS = "gtk+ libxine"
PR = "r2"

# Long list of RDEPENDS_${PN} required to playback mp3/ogg audio and mpeg/mpeg4 video
RDEPENDS_${PN} = "libxine-plugin-vo-out-xshm \
	    libxine-plugin-vo-out-none \
	    libxine-plugin-ao-out-esd \
	    libxine-plugin-ao-out-none \
	    libxine-plugin-inp-file \
	    libxine-plugin-inp-http \
	    libxine-plugin-inp-net \
	    libxine-plugin-inp-mms \
	    libxine-plugin-decode-mad \
	    libxine-plugin-decode-vorbis \
	    libxine-plugin-decode-image \
	    libxine-plugin-decode-ff \
	    libxine-plugin-dmx-audio \
	    libxine-plugin-dmx-mpeg \
	    libxine-plugin-dmx-mpeg-block \
	    libxine-plugin-dmx-mpeg-elem \
	    libxine-plugin-dmx-mpeg-pes \
	    libxine-plugin-dmx-mpeg-ts \
	    libxine-plugin-dmx-ogg \
	    libxine-plugin-dmx-image \
	    libxine-plugin-dmx-avi"

S = "${WORKDIR}/${PN}-0.4.1/"

SRC_URI = "${SOURCEFORGE_MIRROR}/xine/${PN}-0.4.1.tar.gz \
	   file://rhythmbox-volume-max.png \
	   file://rhythmbox-volume-medium.png \
	   file://rhythmbox-volume-min.png \
	   file://rhythmbox-volume-zero.png \
	   file://enhance.patch"

EXTRA_OECONF = " --includedir=${STAGING_INCDIR} \
		--libdir=${STAGING_LIBDIR} \
		--disable-xinetest \
		--with-xine-prefix=${STAGING_DIR_HOST}${layout_exec_prefix}"

LDFLAGS += "-lxine"

inherit autotools pkgconfig

do_configure_prepend () {
	mv ${WORKDIR}/*.png ${S}/pixmaps/
}


SRC_URI[md5sum] = "4a04df0d971a8952fb395b6eb0bc8dab"
SRC_URI[sha256sum] = "48d93ef10f17cffe1ecbfec3078932ae48e08e3c172437e4bdfb22ae87d25421"
