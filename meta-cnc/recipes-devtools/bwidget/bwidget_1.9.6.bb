SUMMARY = "The BWidget Toolkit is a high-level Widget Set for Tcl/Tk built using native Tcl/Tk 8.x namespaces"
HOMEPAGE = "http://tcllib.sourceforge.net"
SECTION = "devel/tcltk"
LICENSE = "tcl"

DEPENDS = 'tcllib'

S = "${WORKDIR}/${PN}-${PV}"

LIC_FILES_CHKSUM = "\
    file://CHANGES.txt;md5=aa5daafc64f6c68dd447cd57af92e139\
"
SRC_URI=" http://sourceforge.net/projects/tcllib/files/BWidget/1.9.6/bwidget-1.9.6.tar.gz"

SRC_URI[md5sum] = "1cc231178e9d119e26c7199b03fcf862"
SRC_URI[sha256sum] = "155e9cf2c6973956a0bbde450f2df358ce1eb97a2b2950d0681a36f861e67553"

inherit autotools
PARALLEL_MAKE = ""

# fix me.  might want a diff dest dir
do_install() {
    mkdir -p ${D}${libdir}
    cp -a ${S}/* ${D}${libdir}
    chmod -R 0644 ${D}${libdir}
} 

PACKAGES = "${PN}"

FILES_${PN} += "${libdir}/* \
                ${libdir}/images/* \
                ${libdir}/tests/* \
                ${libdir}/demo/* \
                ${libdir}/lang/* \
                ${libdir}/BWman/*"


