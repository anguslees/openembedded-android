DESCRIPTION = "A game where you can sing caraoke or play instruments"
HOMEPAGE = "http://performous.org/"
SECTION = "apps"
LICENSE = "GPL"

DEPENDS = "boost glib-2.0 gstreamer pulseaudio pango freetype \
           ffmpeg cairo gtk+ librsvg libxml++ imagemagick jack \
           libglew virtual/libsdl \
          "

PR = "r1"

inherit cmake

SRC_URI = "${SOURCEFORGE_MIRROR}/project/performous/performous/${PV}/Performous-${PV}-Source.tar.bz2"

S = "${WORKDIR}/Performous-${PV}-Source"

OECMAKE_BUILDPATH = "build"

OECMAKE_SOURCEPATH = "../"

FILES_${PN} += " ${datadir}/games/performous/themes/default/* \
                 ${datadir}/games/performous/config/* \
                 ${datadir}/games/performous/xsl/* \
                 ${datadir}/games/performous/sounds/* \                                                                        
                 ${datadir}/games/performous/backgrounds/* \
               "


SRC_URI[md5sum] = "d7eafad29a94e3099c849d3c7208bfac"
SRC_URI[sha256sum] = "4688546a70b09837311e428c233bac091ce5301c7dd4efe21137ca8867af8be0"
