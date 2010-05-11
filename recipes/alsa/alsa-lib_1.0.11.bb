DESCRIPTION = "Alsa sound library"
HOMEPAGE = "http://www.alsa-project.org"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1"
PR = "r4"

# configure.in sets -D__arm__ on the command line for any arm system
# (not just those with the ARM instruction set), this should be removed,
# (or replaced by a permitted #define).
#FIXME: remove the following
ARM_INSTRUCTION_SET = "arm"

SRC_URI = "ftp://ftp.alsa-project.org/pub/lib/alsa-lib-${PV}.tar.bz2"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-cards=pdaudiocf --with-oss=yes"

do_stage() {
	oe_libinstall -so -C src libasound ${STAGING_LIBDIR}/
	install -d ${STAGING_INCDIR}/alsa/sound
	install -m 0644 include/*.h ${STAGING_INCDIR}/alsa/
	install -m 0644 include/sound/ainstr*.h ${STAGING_INCDIR}/alsa/sound/
	install -d ${STAGING_DATADIR}/aclocal
	install -m 0644 utils/alsa.m4 ${STAGING_DATADIR}/aclocal/
}

PACKAGES =+ "alsa-server libasound alsa-conf-base alsa-conf alsa-doc alsa-dev"
FILES_${PN}-dbg += "${libdir}/alsa-lib/*/.debu*"
FILES_libasound = "${libdir}/libasound.so*"
FILES_alsa-server = "${bindir}/*"
FILES_alsa-conf = "${datadir}/alsa/"
FILES_alsa-dev = "${libdir}/pkgconfig/ /usr/include/ ${datadir}/aclocal/*"
FILES_alsa-conf-base = "\
${datadir}/alsa/alsa.conf \
${datadir}/alsa/cards/aliases.conf \
${datadir}/alsa/pcm/default.conf \
${datadir}/alsa/pcm/dmix.conf \
${datadir}/alsa/pcm/dsnoop.conf"

RDEPENDS_libasound = "alsa-conf-base"

SRC_URI[md5sum] = "ef7ae78a0ef08cbeacb295f2518886ab"
SRC_URI[sha256sum] = "6b25f3d22cdb2476233f6dd74880fd88fb65124d4c282704bb9f0bf3fbd4c8d2"
