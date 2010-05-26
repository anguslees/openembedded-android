PR = "r3"
require u-boot.inc

SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
	   file://arm_flags.patch "
# Override whole URI fr Neon since Neon patch is incompatible with arm_flags patch.
SRC_URI_bd-neon = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2 \
                   file://u-boot-1.1.2-neon.patch"
SRC_URI_append_vibren = "ftp://bec-systems.com/pub/pxa255_idp/u-boot/uboot_pxa255-idp_2005-03-23.patch"
SRC_URI_append_mnci   = "file://mnci.patch \
                         file://mnci-jffs2.patch \
                         file://cmd-arm-linux.patch \
                         file://command-names.patch"

SRC_URI_append_magicbox  = "file://u-boot-emetec.patch "


# TODO: SRC_URI_append_rt3000

TARGET_LDFLAGS = ""

UBOOT_MACHINE_mnci   = "mnci_config"
UBOOT_MACHINE_vibren = "pxa255_idp_config"
UBOOT_MACHINE_magicbox = "EMETEC405_config"

inherit base

do_compile () {
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake all
}

#########################################################

RDEPENDS_append_mnci = " hwctrl"

FILES_${PN}_mnci = "/tmp/${UBOOT_IMAGE}"

do_configure_prepend_bd-neon () {
	chmod +x ${S}/Configure
}

do_install_openmn() {
	install -d ${D}/tmp
	install ${S}/u-boot.bin ${D}/tmp/${UBOOT_IMAGE}
}

pkg_postinst_mnci() {
ldconfig
A=/tmp/bootargs
hwctrl kernel_conf_get bootargs >$A
cp /tmp/${UBOOT_IMAGE} /dev/mtdblock/0
rm /tmp/${UBOOT_IMAGE}
hwctrl kernel_conf_set bootargs "`cat $A`"
cat /dev/mtdblock/0 >/dev/null
exit 0
}

SRC_URI[md5sum] = "f10b6954498bab5d08d0f50de381af50"
SRC_URI[sha256sum] = "4f32cfd01f0678080f8e98009ba99ce37f1261d842e3af9a6b3add22005a80ca"
