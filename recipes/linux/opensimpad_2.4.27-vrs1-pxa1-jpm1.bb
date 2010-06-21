DESCRIPTION = "Linux kernel for the SIEMENS SIMpad family of devices."
SECTION = "kernel"
LICENSE = "GPLv2"
KV = "${@bb.data.getVar('PV',d,True).split('-')[0]}"
VRSV = "${@bb.data.getVar('PV',d,True).split('-')[1]}"
PXAV = "${@bb.data.getVar('PV',d,True).split('-')[2]}"
JPMV = "${@bb.data.getVar('PV',d,True).split('-')[3]}"
USBV= "usb20040610"
PR = "r4"

COMPATIBLE_MACHINE = 'simpad'

FILESPATHPKG =. "opensimpad-${PV}:opensimpad:"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.4/linux-${KV}.tar.bz2;name=kernel \
           file://${KV}-${VRSV}.patch \
           file://${KV}-${VRSV}-${PXAV}.patch \
           file://${KV}-${VRSV}-${PXAV}-${JPMV}.patch \
           file://${KV}-mh1.patch \
           file://sound-volume-reversed.patch \
	   file://disable-pcmcia-probe.patch \
           file://mkdep.patch \
           file://defconfig-${MACHINE} \
	   http://www.openswan.org/download/old/openswan-2.2.0-kernel-2.4-klips.patch.gz;name=patch \
           file://mipv6-1.1-v${KV}.patch \
           file://simpad-backlight-if.patch \
           file://simpad-switches-input.patch \
           file://simpad-switches-input2.patch \
           file://simpad-apm.patch \
           file://simpad-ts-noninput.patch \
           file://simpad-pm-updates.patch \
           file://support-128mb-ram.patch \
           file://simpad-proc-sys-board.patch \
           file://simpad-serial.patch \
           file://mppe-20040216.patch \
           file://sa1100-usb-tcl1.patch \
           file://mmc-spi.patch \
           file://iw249_we17-13.diff \
           file://iw240_we18-5.diff \
"
# This applies right after the jpm patch but is useless until we
# have sa1100_udc.c
#           file://${KV}-${VRSV}-${USBV}.patch \

# apply this when we have a patch that allows building with gcc 3.x:
# SRC_URI_append = file://gcc-3.3.patch
# SRC_URI_append = file://machtune-args.patch

S = "${WORKDIR}/linux-${KV}"

inherit kernel

KERNEL_CCSUFFIX = "-3.3.4"
COMPATIBLE_HOST = "arm.*-linux"

SIMPAD_MEM     = '${@bb.data.getVar("SIMPAD_MEMORY_SIZE",d,1)  or "32"}'
SIMPAD_RD      = '${@bb.data.getVar("SIMPAD_RAMDISK_SIZE",d,1) or "32"}'
export CMDLINE = '${@bb.data.getVar("SIMPAD_CMDLINE",d,1) or  "mtdparts=sa1100:512k(boot),1m(kernel),14848k(root),-(home) console=ttySA root=1f02 noinitrd jffs2_orphaned_inodes=delete rootfstype=jffs2 "}'
#EXTRA_OEMAKE = ""

module_conf_sa1100_ir = "alias irda0 sa1100_ir"

do_configure() {
       install -m 0644 ${WORKDIR}/defconfig-${MACHINE} ${S}/.config || die "No default configuration for ${MACHINE} available."

	mem=${SIMPAD_MEM}
	rd=${SIMPAD_RD}
        mempos=`echo "obase=16; $mem * 1024 * 1024" | bc`
        rdsize=`echo "$rd * 1024" | bc`
        total=`expr $mem + $rd`
        addr=`echo "obase=16; ibase=16; C0000000 + $mempos" | bc`

        if [ "$rd" == "0" ]
        then
                echo "# CONFIG_MTD_MTDRAM is not set" >> ${S}/.config
        else
                echo "CONFIG_MTD_MTDRAM=y"           >> ${S}/.config
                echo "CONFIG_MTDRAM_TOTAL_SIZE=$rdsize"     >> ${S}/.config
                echo "CONFIG_MTDRAM_ERASE_SIZE=1"           >> ${S}/.config
                echo "CONFIG_MTDRAM_ABS_POS=$addr"          >> ${S}/.config
        fi
	if [ "$total" == "128" ]
        then
                echo "CONFIG_SA1100_SIMPAD_128M=y"            >> ${S}/.config
        else
                echo "# CONFIG_SA1100_SIMPAD_128M is not set" >> ${S}/.config
	fi
	if [ "$total" == "32" ]
        then
                echo "CONFIG_SA1100_SIMPAD_SINUSPAD=y"            >> ${S}/.config
        else
                echo "# CONFIG_SA1100_SIMPAD_SINUSPAD is not set" >> ${S}/.config
	fi
	echo "CONFIG_CMDLINE=\"${CMDLINE} mem=${mem}M\"" >> ${S}/.config
        oe_runmake oldconfig
}
SRC_URI[kernel.md5sum] = "59a2e6fde1d110e2ffa20351ac8b4d9e"
SRC_URI[kernel.sha256sum] = "4e4976724fb4c9340d5e9acc69f9d99789641384a6e1bbcc5323fc8832d6661a"
SRC_URI[patch.md5sum] = "5c54040bba6fea2bfb47df01056e953f"
SRC_URI[patch.sha256sum] = "d35213dc854f1e1a08512154c7a92fb94d9f0506cc5107f8b2f248412679fb53"
