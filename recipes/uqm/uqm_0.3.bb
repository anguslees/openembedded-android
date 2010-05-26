DESCRIPTION = "Star Control 2 source port using SDL (see sc2.sourceforge.net)"
SECTION = "games"
PRIORITY = "optional"
DEPENDS = "virtual/libsdl libsdl-image libsdl-net libvorbis libogg zlib"
SECTION = "opie/games"
PRIORITY = "optional"
LICENSE = "GPL"

PR = "r1"

S = "${WORKDIR}/uqm-${PV}"

SRC_URI = "${SOURCEFORGE_MIRROR}/sc2/uqm-${PV}-source.tgz \
           file://build-opts.sh \
           file://build-oe.patch;striplevel=0"

do_configure() {
	install ${WORKDIR}/build-opts.sh ${S}/
	./build-opts.sh ${STAGING_DIR_HOST}${layout_prefix} ${STAGING_BINDIR} ${STAGING_LIBDIR}
}

do_compile() {
	export ARCH="${TARGET_ARCH}"
	export CC="${CC}"
	export STAGING_INCDIR="${STAGING_INCDIR}"
	export STAGING_LIBDIR="${STAGING_LIBDIR}"
	./build.sh uqm
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 uqm ${D}${bindir}
}

SRC_URI[md5sum] = "6abcdc3caf7efd3bd978332743ee7568"
SRC_URI[sha256sum] = "e42ac3b45c1c8a3199bbd9b666e9225d76bc18d902339c54cbb4df3a75909e53"
