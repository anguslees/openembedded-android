DESCRIPTION = "OpenEZX 2.6 Linux Development Kernel for the Motorola EZX GSM phones"
AUTHOR = "The OpenEZX Team <openezx-devel@lists.openezx.org>"
HOMEPAGE = "http://www.openezx.org"
SRCREV = "c485cc5953bbebdab1c52032754accca75031837"
KV = "2.6.34-oe"
PV = "${KV}+gitr${SRCREV}"
PR = "r5"

require linux.inc

# Make sure not to use thumb[-interworking]
ARM_INSTRUCTION_SET = "arm"
THUMB_INTERWORKING = "no"

SRC_URI = "\
  git://git.openezx.org/openezx.git;protocol=git;branch=ezx/current \
"
S = "${WORKDIR}/git"

##############################################################
# The kernel image used to reside on a seperate flash partition
# It can be flashed from userspace, so we should package it anyways
# (flash_unlock /dev/mtdX && flash_eraseall /dev/mtdX && flashcp /boot/zImage /dev/mtdX)

COMPATIBLE_HOST = "arm.*-linux"
COMPATIBLE_MACHINE = '(a780|e680|a910|a1200|rorkre2|rokre6)'

# Provide a fallback kernel command line, even if parameters should be given
# via boot_usb or gen-blob
CMDLINE = "console=tty1 root=/dev/mmcblk0p2 rootfstype=ext2 rootdelay=3 ip=192.168.0.202:192.168.0.200:192.168.0.200:255.255.255.0"
ARM_KEEP_OABI = "1"

###############################################################
# module configs specific to this kernel
#
#module_autoload_pxaficp_ir = "pxaficp_ir"
#module_autoload_snd-pcm-oss = "snd-pcm-oss"

do_configure_prepend() {
	install -m 0644 ${S}/arch/arm/configs/ezx_defconfig ${WORKDIR}/defconfig
}

# linux.inc overrides LOCAVERSION but we like to have one
do_compile_prepend() {
	sed -i -e '/CONFIG_LOCALVERSION=/d' ${S}/.config
	echo 'CONFIG_LOCALVERSION="-oe"' >>${S}/.config
}
