DESCRIPTION = "Kernel drivers for the PowerVR SGX chipset found in the omap3 SoCs"
LICENSE = "GPLv2"

PR_append = "b"

# download required binary distribution from:
# http://software-dl.ti.com/dsps/forms/export.html?prod_no=/OMAP35x_Graphics_SDK_setuplinux_3_01_00_05.bin

TI_BIN_UNPK_CMDS="Y: qY:workdir:Y"
require ../ti/ti-eula-unpack.inc

SGXPV = "3_01_00_06"
IMGPV = "1.4.14.2616"
BINFILE := "OMAP35x_Graphics_SDK_setuplinux_${SGXPV}.bin"

SRC_URI = "file://OMAP35x_Graphics_SDK_setuplinux_${SGXPV}.bin \
"
S = "${WORKDIR}/OMAP35x_Graphics_SDK_${SGXPV}/GFX_Linux_KM"

inherit module

PVRBUILD = "release"

PACKAGE_STRIP = "no"

MAKE_TARGETS = " BUILD=${PVRBUILD} TI_PLATFORM=omap3630"

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp ${S}/pvrsrvkm.ko \
	   ${S}/services4/3rdparty/dc_omap3430_linux/omaplfb.ko  \
	   ${S}/services4/3rdparty/bufferclass_ti/bufferclass_ti.ko \
	   ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
