DESCRIPTION = "Amiga Emulator based on SDL"
SECTION = "base"
PRIORITY = "optional"
DEPENDS = "virtual/libsdl zlib"
LICENSE = "GPL"
PR = "r1"

SRC_URI = "http://rcdrummond.net/uae/e-uae-${PV}/e-uae-${PV}.tar.bz2 \
           file://configure.patch"

inherit autotools

export SDL_CONFIG = "${STAGING_BINDIR_CROSS}/sdl-config"

EXTRA_OECONF = "--with-hostcc=gcc --disable-ui --without-x \
		--without-gtk --enable-jit --disable-natmem \
		--with-zlib=${STAGING_LIBDIR}/.."

CFLAGS_append = " -DSTAT_STATFS2_BSIZE=1 "
CXXFLAGS_append = " -DSTAT_STATFS2_BSIZE=1 "
PARALLEL_MAKE = ""

export S
export PKG_CONFIG="${STAGING_BINDIR_NATIVE}/pkg-config"

do_configure_prepend () {
	sed -i -e s:getline:etline:g ./src/gui-none/nogui.c
	touch NEWS AUTHORS ChangeLog
}

SRC_URI[md5sum] = "9fc186f9256d04f940304044e29175ef"
SRC_URI[sha256sum] = "afc8b30fb9aa0819a4e53b3eb0db8e658e5a2b23d7dbf436f6b5a49b2269da86"
