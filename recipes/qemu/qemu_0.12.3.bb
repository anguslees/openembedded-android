LICENSE = "GPL"
DEPENDS = "zlib"

PR = "r3"

SRC_URI = "\
    http://download.savannah.gnu.org/releases/qemu/qemu-${PV}.tar.gz;name=qemu-${PV} \
    file://06_exit_segfault.patch;striplevel=0 \
    file://11_signal_sigaction.patch;striplevel=0 \
    file://22_net_tuntap_stall.patch \
    file://31_syscalls.patch;striplevel=0 \
    file://32_syscall_sysctl.patch;striplevel=0 \
    file://52_ne2000_return.patch \
    file://63_sparc_build.patch;striplevel=0 \
    file://64_ppc_asm_constraints.patch \
    file://66_tls_ld.patch;striplevel=0 \
    file://91-oh-sdl-cursor.patch;striplevel=0 \
    file://fix_segfault.patch \
    file://fix_baum_c_compilation.patch \
    file://fix_fortify_source_compilation.patch \
    file://3f26c1227e3b08010f2a65379cecf4cb4b5933fa.patch \
    file://c5883be23519921254c6940873ee8db04979c20a.patch \
    file://fallback.to.safe.mmap_min_addr.patch \
    "

SRC_URI[qemu-0.12.3.sha256sum] = "3ce26f8fb0a59418b2064a26bac4b40ea4e493acbc3df7ad5932635477fade4b"
SRC_URI[qemu-0.12.3.md5sum] = "d215e4568650e8019816397174c090e1"

BBCLASSEXTEND="native"

S = "${WORKDIR}/qemu-${PV}"

EXTRA_OECONF += " --disable-curl --disable-sdl --disable-strip"

EXTRA_OECONF_append_virtclass-native = " --extra-cflags="-I${STAGING_INCDIR_NATIVE}""

inherit autotools

do_configure() {
	${S}/configure --prefix=${prefix} ${EXTRA_OECONF}
}
