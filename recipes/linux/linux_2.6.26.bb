require linux.inc

PR = "r10"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_boc01 = "1"
DEFAULT_PREFERENCE_mpc8313e-rdb = "1"
DEFAULT_PREFERENCE_canyonlands = "1"
DEFAULT_PREFERENCE_topas910 = "1"


SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.26.tar.bz2;name=kernel \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${PV}.8.bz2;apply=yes;name=stablepatch \
           file://defconfig"

SRC_URI_append_boc01 = "\
	file://boc01.dts \
	file://fsl-elbc-nand-backport.patch \
	file://002-081105-headers.patch \
	file://004-081105-usb.patch \
	file://005-081217-isl12024.patch \
	file://006-081216-at24c32.patch \
	file://007-081216-lm73.patch \
	file://008-081127-spi.patch \
	file://010-081105-mii.patch \
	file://011-081202-gpio.patch \
	file://012-081222-cy3218-btns.patch \
	file://013-081212-lcd.patch \
	"

SRC_URI_append_mpc8313e-rdb = "\
	file://cdc-ether-hack.patch \
	file://fsl-elbc-nand-backport.patch \
	file://mpc8313e-rdb-leds.patch \
	file://mpc8313e-rdb-cardbus.patch \
	file://mpc8313e-rdb-eth-fixed.patch \
	"

SRC_URI_append_topas910 = "http://www.bplan-gmbh.org/data/toshiba/topas/linux/2.6.26.5/patch_2.6.26.5_topas910.bz2;apply=yes;name=topaspatch"

# see http://bugzilla.kernel.org/show_bug.cgi?id=11143
do_install_append() {
	if [ -f arch/${ARCH}/lib/crtsavres.o ]; then
		mkdir -p $kerneldir/arch/${ARCH}/lib
		cp -a arch/${ARCH}/lib/crtsavres.o $kerneldir/arch/${ARCH}/lib/
	fi
}


SRC_URI[kernel.md5sum] = "5169d01c405bc3f866c59338e217968c"
SRC_URI[kernel.sha256sum] = "666488e2511393fdb901eaf1e67275bcc38ab37c930e8a9adb290a95c1721a2a"
SRC_URI[stablepatch.md5sum] = "e27c07bb82e02532e874758980141281"
SRC_URI[stablepatch.sha256sum] = "611f7e118b5b09898493eab4c3071771742f2f3826b5733fa2f47284a38fbcf3"

SRC_URI[topaspatch.md5sum] = "092957fc9e029010106c4fa59ef2c098"
SRC_URI[topaspatch.sha256sum] = "eefecb157633d2be256c0f6a2d46ab764027bbb906cf5c250be67b95e80767bb"
