DESCRIPTION = "APEX Boot Loader"
SECTION = "misc"
PRIORITY = "optional"
HOMEPAGE = "http://wiki.buici.com/twiki/bin/view/Main/ApexBootloader"
LICENSE = "GPL"
PR = "r3"

inherit siteinfo

SRC_URI = "ftp://ftp.buici.com/pub/apex/apex-${PV}.tar.gz \
	   file://defconfig"
S = ${WORKDIR}/apex-${PV}

CMDLINE_CONSOLE = "console=${@bb.data.getVar("KERNEL_CONSOLE",d,1) or "ttyS0"}"

CMDLINE_ROOT  ?= "root=/dev/mtdblock4 rootfstype=jffs2 rw"

CMDLINE_DEBUG ?= ""

EXTRA_OEMAKE_append = " CROSS_COMPILE=${CROSS_DIR}/bin/${HOST_PREFIX}"

oe_runmake() {
	oenote make ${PARALLEL_MAKE} CROSS_COMPILE=${CROSS_DIR}/bin/${TARGET_PREFIX} "$@"
	make ${PARALLEL_MAKE} LDFLAGS= CROSS_COMPILE=${CROSS_DIR}/bin/${TARGET_PREFIX} "$@" || die "oe_runmake failed"
}

# Set the correct CONFIG_USER_xxx_ENDIAN and CONFIG_CMDLINE at the head
# of the .config file and remove any settings in defconfig then append
# defconfig to .config
do_configure() {
	rm -f ${S}/.config
	if [ "x${SITEINFO_ENDIANESS}" = "xbe" ]; then
	  sed -e 's/.*CONFIG_USER_BIGENDIAN.*/CONFIG_USER_BIGENDIAN=y/' \
	      -e 's/.*CONFIG_BIGENDIAN.*/CONFIG_BIGENDIAN=y/' \
	      -e 's/.*CONFIG_TARGET_DESCRIPTION.*/CONFIG_TARGET_DESCRIPTION=\"OpenEmbedded NSLU2\/BE (16MiB Flash)\"/' \
	      -e 's|CONFIG_ENV_DEFAULT_CMDLINE=|CONFIG_ENV_DEFAULT_CMDLINE=\"${CMDLINE_CONSOLE} ${CMDLINE_ROOT} ${CMDLINE_DEBUG}\"|' \
	      -e 's|CONFIG_ENV_DEFAULT_CMDLINE_ALT=|CONFIG_ENV_DEFAULT_CMDLINE_ALT=\"${CMDLINE_CONSOLE} ${CMDLINE_ROOT} ${CMDLINE_DEBUG}\"|' \
		${WORKDIR}/defconfig > ${S}/.config
	elif [ "x${SITEINFO_ENDIANESS}" = "xle" ]; then
	  sed -e 's/.*CONFIG_USER_LITTLEENDIAN.*/CONFIG_USER_LITTLEENDIAN=y/' \
	      -e 's/.*CONFIG_LITTLEENDIAN.*/CONFIG_LITTLEENDIAN=y/' \
	      -e 's/.*CONFIG_TARGET_DESCRIPTION.*/CONFIG_TARGET_DESCRIPTION=\"OpenEmbedded NSLU2\/LE (16MiB Flash)\"/' \
	      -e 's|CONFIG_ENV_DEFAULT_CMDLINE=|CONFIG_ENV_DEFAULT_CMDLINE=\"${CMDLINE_CONSOLE} ${CMDLINE_ROOT} ${CMDLINE_DEBUG}\"|' \
	      -e 's|CONFIG_ENV_DEFAULT_CMDLINE_ALT=|CONFIG_ENV_DEFAULT_CMDLINE_ALT=\"${CMDLINE_CONSOLE} ${CMDLINE_ROOT} ${CMDLINE_DEBUG}\"|' \
		${WORKDIR}/defconfig > ${S}/.config
	else
	  oefatal do_configure cannot determine endianess
	fi
	oe_runmake oldconfig
}

DEPENDS += "devio-native"

do_stage() {
	install -d ${STAGING_LOADER_DIR}
	if [ "x${SITEINFO_ENDIANESS}" = "xbe" ]; then
		cp src/arch-arm/rom/apex.bin ${STAGING_LOADER_DIR}/apex-nslu2-16mb.bin
	elif [ "x${SITEINFO_ENDIANESS}" = "xle" ]; then
		devio '<<'src/arch-arm/rom/apex.bin >${STAGING_LOADER_DIR}/apex-nslu2-16mb.bin 'xp $,4'
	else
		oefatal do_populate_sysroot cannot determine endianess
	fi
}

SRC_URI[md5sum] = "22fb46e76c8221c7bcc9734602367460"
SRC_URI[sha256sum] = "472e12897931d9e5dcf2d1e3332acfc467aafd0f62e612896a9c71dd0d16d950"
