DESCRIPTION = "Linux Kernel for the EFIKA dev platform"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r2"

COMPATIBLE_MACHINE = "efika"

FILESPATHPKG .= ":linux-efika-2.6.20"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.20.tar.bz2;name=kernel \
           file://0001-powerpc-serial-Dispose-irq-mapping-when-done-in-mpc52xx_serial.c.txt;p=1;apply=yes \
           file://0003-powerpc-Add-device-tree-fixups-for-the-EFIKA.txt;p=1;apply=yes \
           file://0004-powerpc-Use-common-52xx-of_platform-probe-code-for-EFIKA.txt;p=1;apply=yes \
           file://0005-powerpc-Restore-proper-link-order-in-platform.txt;p=1;apply=yes \
           file://0006-Rework-the-OHCI-quirk-mecanism-as-suggested-by-David.txt;p=1;apply=yes \
           file://0007-Implement-support-for-split-endian-OHCI.txt;p=1;apply=yes \
           file://0008-ohci-Rework-bus-glue-integration-to-allow-several-at-once.txt;p=1;apply=yes \
           file://0009-ohci-Add-support-for-OHCI-controller-on-the-of_platform-bus.txt;p=1;apply=yes \
           file://0010-libata-Add-support-for-the-MPC52xx-ATA-controller.txt;p=1;apply=yes \
           file://0011-ohci-Whitespace-and-typo-fix-in-ohci-ppc-of.c.txt;p=1;apply=yes \
           file://0012-ata-Fix-pata_mpc52xx.c-compatible-list.txt;p=1;apply=yes \
           file://0013-powerpc-serial-Fix-mpc52xx_uart.c-compatible-list.txt;p=1;apply=yes \
           file://0014-powerpc-Small-cleanup-of-EFIKA-platform.txt;p=1;apply=yes \
           file://0015-powerpc-Add-a-unified-uevent-handler-for-bus-based-on-of_device.txt;p=1;apply=yes \
           file://0016-macintosh-Use-the-new-of_device-common-uevent-handler.txt;p=1;apply=yes \
           file://0017-powerpc-Add-uevent-handler-for-of_platform_bus.txt;p=1;apply=yes \
           file://0018-powerpc-Add-uevent-handler-for-ibmebus.txt;p=1;apply=yes \
           file://0019-MPC5200-Bestcomm-platform-driver.txt;p=1;apply=yes \
           file://0020-Fec-MPC5200-eth-driver.txt;p=1;apply=yes \
           file://0021-POWERPC-Copy-bestcomm-support-files-into-arch-powerpc.txt;p=1;apply=yes \
           file://0022-MPC52xx-PCI-now-working-on-lite5200.-ugly-but-working.txt;p=1;apply=yes \
           file://0023-POWERPC-Make-FEC-work-on-the-lite5200.txt;p=1;apply=yes \
           file://0024-Add-missing-function-prototype.txt;p=1;apply=yes \
           file://0025-POWERPC-Misc-EFIKA-fixups-for-rtas-chrp.txt;p=1;apply=yes \
           file://0026-POWERPC-Cleanup-mpc52xx-PCI-support.txt;p=1;apply=yes \
           file://0027-POWERPC-Change-name-of-mpc52xx-pci-support-file-in-Makefile.txt;p=1;apply=yes \
           file://0028-POWERPC-Change-link-order-so-mpc52xx-fec-always-shows-up-as-eth0.txt;p=1;apply=yes \
           file://0029-POWERPC-Fixup-pr_print-format-for-mpc52xx-pci-support.txt;p=1;apply=yes \
           file://0030-POWERPC-Add-mpc52xx-lite5200-PCI-support.txt;p=1;apply=yes \
           file://0031-sound-Add-support-for-the-MPC52xx-PSC-AC97-Link.txt;p=1;apply=yes \
           file://0032-POWERPC-EFIKA-Adds-missing-interrupts-from-bestcomm-node.txt;p=1;apply=yes \
           file://0033-EFIKA-fullduplex-prpl_aln.txt;p=1;apply=yes \
           file://v4l.diff;p=1 \
           http://www.kernel.org/pub/linux/kernel/v2.6/patch-2.6.20.20.bz2;p=1;apply=yes;name=stablepatch \
           http://people.redhat.com/mingo/cfs-scheduler/sched-cfs-v2.6.20.20-v22.patch;p=1;name=shedpatch \
           file://weaken-div64_32-symbol.patch \
           file://defconfig \
		   "

S = "${WORKDIR}/linux-2.6.20"

inherit kernel

export ARCH="powerpc"


do_configure() {
		install -m 644 ${WORKDIR}/defconfig ${S}/.config
		make ARCH=${ARCH} oldconfig
}

do_install_append () {
#need ppc platforms includes + friends in order for external kernel modules to compile as headers as still split

       install -d $kerneldir/arch/
       cp -pPR arch/ppc $kerneldir/arch/
       cp -pPR arch/powerpc $kerneldir/arch/

       install -d $kerneldir/include/asm
       cp -pPR include/asm-powerpc $kerneldir/include/
       cp -pPR include/asm-ppc $kerneldir/include/
}

SRC_URI[kernel.md5sum] = "34b0f354819217e6a345f48ebbd8f13e"
SRC_URI[kernel.sha256sum] = "2c14ada1ac7d272e03b430d3a530d60fc9ec69cc8252382aa049afba7d2b8558"
SRC_URI[stablepatch.md5sum] = "08b2c1e9759ca409a0d2ca49db1bbedb"
SRC_URI[stablepatch.sha256sum] = "0417fc3609f5746bce6016862e7f83e83e98e17e5ae77887bf66b37748ab261c"
SRC_URI[shedpatch.md5sum] = "0fd84a82c51f67d2d0531aab78f66d2a"
SRC_URI[shedpatch.sha256sum] = "fc5cd165ecc07d2e254412f39a17a26a753bf081a0e02ed5db0b02d2f6bacc6f"
