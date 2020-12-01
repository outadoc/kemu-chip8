//
//  Chip8DisplayView.swift
//  CHIP-8 (iOS)
//
//  Created by Baptiste on 30/11/2020.
//

import Foundation
import UIKit
import lib_kemu
import lib_chip8

class Chip8DisplayView : UIView, FrameConsumer {
    
    private let scaleFactor = 10
    private var currentFrame: UByteArray2? = nil

    private var background: Color
    private var foreground: Color

    var theme: Theme = .whiteOnBlack {
        didSet {
            background = theme.background
            foreground = theme.foreground
            setNeedsDisplay()
        }
    }

    public required init?(coder aDecoder: NSCoder) {
        background = theme.background
        foreground = theme.foreground
        super.init(coder: aDecoder)
    }

    public override init(frame: CGRect) {
        background = theme.background
        foreground = theme.foreground
        super.init(frame: frame)
    }
    
    override var intrinsicContentSize: CGSize {
        return CGSize(
            width: (Int(Chip8Constants().DISPLAY_WIDTH) * scaleFactor),
            height: (Int(Chip8Constants().DISPLAY_HEIGHT) * scaleFactor)
        )
    }

    func displayFrame(frame: UByteArray2?) {
        currentFrame = frame
        setNeedsDisplay()
    }
    
    override func draw(_ rect: CGRect) {
        let ctx = UIGraphicsGetCurrentContext()
        if let frame = currentFrame {
            var i = 0
            while (frame.iterator().hasNext()) {
                let pixel = frame.iterator().next() as! NSNumber

                // Byte i in buffer is at screen position (i mod width, i / width)
                let x = i % Int(Chip8Constants().DISPLAY_WIDTH)
                let y = i / Int(Chip8Constants().DISPLAY_WIDTH)

                // Draw foreground color if bit is set, background otherwise
                let color = pixel == 0x0 ? background : foreground
                ctx?.setFillColor(color.toCGColor())

                ctx?.fill(
                    CGRect(
                        x: CGFloat(x * scaleFactor),
                        y: CGFloat(y * scaleFactor),
                        width: CGFloat(scaleFactor),
                        height: CGFloat(scaleFactor)
                    )
                )
                
                i += 1
            }
        } else {
            super.draw(rect)
        }
    }
}

extension Color {
    
    func toCGColor() -> CGColor {
        return CGColor(red: CGFloat(r), green: CGFloat(g), blue: CGFloat(b), alpha: 1.0)
    }
}
