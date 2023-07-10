import styles from './Dropdown.module.scss'

type DropdownProps = {
  handleOption: React.Dispatch<React.SetStateAction<string>>
  options: string[]
  selectedOption: string
  closeDropdown: () => void
}

function Dropdown({ handleOption, options, selectedOption, closeDropdown }: DropdownProps) {
  const selectOption = (option: string) => {
    handleOption(option)
    closeDropdown()
  }

  return (
    <>
      <div role='presentation' className={styles.background} onClick={() => closeDropdown()} />
      <ul className={styles.dropDown}>
        {options.map(opt => (
          <li key={opt}>
            <button
              className={opt === selectedOption ? styles.selectedOpt : styles.option}
              type='button'
              onClick={() => selectOption(opt)}
            >
              {opt}
            </button>
          </li>
        ))}
      </ul>
    </>
  )
}

export default Dropdown
