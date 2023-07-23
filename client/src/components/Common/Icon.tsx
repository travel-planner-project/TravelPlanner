import SpriteIcon from '../../assets/sprite-icon.svg'

type IconNameType =
  | 'chatting-plus'
  | 'chatting-dots'
  | 'box-arrow-left'
  | 'box-arrow-right'
  | 'calendar'
  | 'camera'
  | 'check-box'
  | 'uncheck-box'
  | 'x'
  | 'skyblue-x-circle'
  | 'pencil-box'
  | 'trash-bin'
  | 'alarm-gray'
  | 'alarm-blue'
  | 'calculator-gray'
  | 'calculator-blue'
  | 'file-text-fill-gray'
  | 'file-text-fill-blue'
  | 'check-square-gray'
  | 'check-square-blue'
  | 'add-person'
  | 'profile'
  | 'eye'
  | 'eye-close'
  | 'minus-square'
  | 'plus-square'
  | 'x-circle-blue'

type IconProps = {
  name: IconNameType
  size?: number
}

function Icon({ name, size = 24 }: IconProps) {
  return (
    <svg width={size} height={size}>
      <use href={`${SpriteIcon}#${name}`} />
    </svg>
  )
}

export default Icon
