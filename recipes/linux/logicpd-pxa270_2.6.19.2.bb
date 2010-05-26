SECTION = "kernel"
DESCRIPTION = "Linux kernel for the LogicPD Zoom(PXA270 ref design)"
LICENSE = "GPLv2"
PR = "r1"

SRC_URI = "ftp://ftp.kernel.org/pub/linux/kernel/v2.6/linux-2.6.19.2.tar.bz2 \
	   file://asoc-v0.12.4.patch \
	   file://pxafb_fix_params-r2.patch \
           file://pxa_irda_susres_fix-r0.patch \
           file://pxa_keys-r5.patch \
           file://pxa_timerfix-r0.patch \
           file://input_power-r6.patch \
           file://pxa25x_cpufreq-r1.patch \
           file://pm_changes-r1.patch \
           file://usb_add_epalloc-r3.patch \
           file://usb_pxa27x_udc-r3.patch \
           file://kexec-arm-r3.patch \
           file://pxa27x_overlay-r4.patch \
           file://xscale_cache_workaround-r1.patch \
           file://ucb1400-touchscreen.patch \
           file://config-nr-tty-devices.patch \
	   "

SRC_URI_append_logicpd-pxa270 = "\
           file://logicpd-pxa270-cf-hack.patch;striplevel=0 \
           file://logicpd-pxa270-flash.patch;striplevel=0 \
           file://logicpd-pxa270-hardware-id-hack.patch;striplevel=0 \
           file://logicpd-pxa270-smc91x.patch;striplevel=0 \
           file://logicpd-pxa270-lcd-osd024ttea2.patch;striplevel=0 \
           file://defconfig-logicpd-pxa270 \
           "

S = "${WORKDIR}/linux-2.6.19.2"

COMPATIBLE_HOST = 'arm.*-linux'

inherit kernel
inherit package

ARCH = "arm"

FILES_kernel-image = ""

do_configure_prepend() {
#	install -m 0644 ${S}/arch/arm/configs/lpd270_defconfig ${S}/.config
	install -m 0644 ${WORKDIR}/defconfig-logicpd-pxa270 ${S}/.config

}

COMPATIBLE_MACHINE = "logicpd-pxa270"

SRC_URI[md5sum] = "ca0ce8f288e8ae93ac243b568f906bf8"
SRC_URI[sha256sum] = "c55c52caa613d1f25718b35811e4614d9712b9e1de56a91aa73c867f351a540b"
